package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.controller.DTObeans.ChangePasswordForm;
import umk.zychu.inzynierka.controller.DTObeans.EditAccountForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterUserBean;
import umk.zychu.inzynierka.model.User;

public interface UserService {	
	User getUser(String email);
	User getUser(Integer id);
	User saveUser(User user);
	Boolean checkIfUserExists(String email);
	void createNewUser(RegisterUserBean registerUserBean);
	Boolean checkOldPasswordCorrectness(String oldPassword);
	void changePassword(ChangePasswordForm form);
	void updateUserDetails(EditAccountForm form);
	void removeAccount();

	void delete(User user);
}
