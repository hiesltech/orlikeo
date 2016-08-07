package umk.zychu.inzynierka.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.OrlikService;
import umk.zychu.inzynierka.service.UserAdminService;
import umk.zychu.inzynierka.service.UserService;

import java.util.HashSet;
import java.util.Set;

@Service("myUserDetailsService")
public class myUserDetailsServiceImp implements UserDetailsService {

	@Autowired
	private UserService userService;
	@Autowired
	UserAdminService userAdminService;
	@Autowired
	OrlikService orlikService;

	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException, DataAccessException {
		User user = userService.getUser(email);
		if (user == null) {
			throw new UsernameNotFoundException(
					"Nie znaleziono uzytkownika o loginie: " + email);
		}
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		Boolean isOrlikManager = orlikService.isOrlikManager(user);
		if(userAdminService.hasAdminRight(user)) {
			roles.add(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.toString()));
/*			roles.add(new SimpleGrantedAuthority(Roles.ROLE_ANIMATOR.toString()));*/
			roles.add(new SimpleGrantedAuthority(Roles.ROLE_USER.toString()));
		}
		else if(isOrlikManager){
			roles.add(new SimpleGrantedAuthority(Roles.ROLE_ANIMATOR.toString()));
		}else{
			roles.add(new SimpleGrantedAuthority(Roles.ROLE_USER.toString()));
		}

		boolean enabled = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		return new org.springframework.security.core.userdetails.User(
				user.getEmail(), user.getPassword(), enabled,
				accountNonExpired, credentialsNonExpired, accountNonLocked,
				roles);
	}
}