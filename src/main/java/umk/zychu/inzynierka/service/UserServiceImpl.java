package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.controller.DTObeans.ChangePasswordForm;
import umk.zychu.inzynierka.controller.DTObeans.EditAccountForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterUserBean;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserNotification;
import umk.zychu.inzynierka.repository.FriendshipDaoRepository;
import umk.zychu.inzynierka.repository.UserDaoRepository;

import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	UserDaoRepository userDAO;
	@Autowired 
	FriendshipDaoRepository friendshipDAO;
	@Autowired
	UserEventService userEventService;
	@Autowired
	BCryptPasswordEncoder passEncoder;
	@Autowired
	EventService eventService;
	@Autowired
	UserNotificationsService userNotificationsService;
	@Autowired
	EventToApproveService eventToApproveService;
	@Autowired
	FriendshipService friendshipService;

	@Override
	public User getUser(String email) {
		Optional<User> userOpt = userDAO.findAll().stream().filter(u -> u.getEmail().equals(email)).findFirst();
		if(userOpt.isPresent())
			return userOpt.get();
		else 
			return null;
	}
		
	@Override
	public User getUser(Integer id) {
		Optional<User> userOpt = userDAO.findAll().stream().filter(u -> u.getId() == id).findFirst();
		if(userOpt.isPresent())
			return userOpt.get();
		else 
			return null;
	}
	
	@Override
	public User saveUser(User user) {
		return userDAO.save(user);
	}

	@Override
	public Boolean checkIfUserExists(String email) {
		return getUser(email) != null;
	}
	
	@Override
	public void createNewUser(RegisterUserBean registerUserBean) {
		User newUser = new User();
		newUser.setEmail(registerUserBean.getEmail());
		newUser.setPassword(passEncoder.encode(registerUserBean.getPassword()));
		saveUser(newUser);
	}
	
	@Override
	public Boolean checkOldPasswordCorrectness(String oldPassword) {
		User user = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		if(passEncoder.matches(oldPassword, user.getPassword())){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void changePassword(ChangePasswordForm form) {	
		User user = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		user.setPassword(passEncoder.encode(form.getNewPassword()));
		userDAO.saveAndFlush(user);
	}

	@Override
	public void updateUserDetails(EditAccountForm form) {
		User user = getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		user.setName(form.getName());
		user.setSurname(form.getSurname());
		user.setDateOfBirth(form.getDateOfBirth());
		user.setPosition(form.getPosition());
		user.setWeight(form.getWeight());
		user.setHeight(form.getHeight());
		user.setFoot(form.getFoot());
		userDAO.saveAndFlush(user);
	}

	@Override
	@Transactional
	public void removeAccount() {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		User user = getUser(username);
		user.getOrganizedEventsList()
						.stream()
						.flatMap(e -> {
							return userNotificationsService.findAll().stream()
									.filter(n -> n.getLink().contains("event")
											&& n.getLink().contains(e.getId().toString()));
						})
						.forEach(un -> userNotificationsService.delete(un));
		user.getUserFriends().forEach(u -> {
			UserNotification un = new UserNotification(username + " usunął konto", "Wszystkie wydarzenia użytkownika zostały unieważnione", u);
			userNotificationsService.save(un);
		});
		delete(user);

	}

	@Override
	public void delete(User user){
		userDAO.delete(user);
	}
}
