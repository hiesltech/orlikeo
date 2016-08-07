package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.repository.UserAdminDaoRepository;

/**
 * Created by emagdnim on 2015-10-06.
 */
@Service
public class UserAdminServiceImp implements UserAdminService {

    @Autowired
    UserAdminDaoRepository userAdminDAO;

    @Override
    public Boolean hasAdminRight(User user) {
        if(userAdminDAO.findAll().stream()
                .filter(a -> a.getAdmin().equals(user))
                .count() > 0 )
            return true;
        return false;
    }
}
