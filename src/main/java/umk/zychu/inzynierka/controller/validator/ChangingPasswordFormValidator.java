package umk.zychu.inzynierka.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import umk.zychu.inzynierka.controller.DTObeans.ChangePasswordForm;
import umk.zychu.inzynierka.service.UserService;

@Component
public class ChangingPasswordFormValidator implements Validator{

	@Autowired
	UserService userService;
	
	private static final String EMPTY_OR_WHITESPACE_OLD_PASSWORD = "web.register.validation.password.required";
	private static final String EMPTY_OR_WHITESPACE_NEW_PASSWORD = "web.register.validation.password.required";
	private static final String EMPTY_OR_WHITESPACE_REPEATED_PASSWORD = "web.register.validation.repeated_password.required";
	private static final String OLD_PASSWORDS_DO_NOT_MATCH = "web.password.change.old_password.not_match";
	private static final String PASSWORDS_DO_NOT_MATCH = "web.register.validation.repeated_password.not_match";
	private static final String PASSWORD_TOO_SHORT = "web.register.validation.password.short";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ChangePasswordForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "oldPassword", EMPTY_OR_WHITESPACE_OLD_PASSWORD);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPassword", EMPTY_OR_WHITESPACE_NEW_PASSWORD);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "newPasswordConfirm", EMPTY_OR_WHITESPACE_REPEATED_PASSWORD);	
		ChangePasswordForm form = (ChangePasswordForm) target;
		if(!userService.checkOldPasswordCorrectness(form.getOldPassword())){
			errors.rejectValue("oldPassword", OLD_PASSWORDS_DO_NOT_MATCH);
		}
		if(form.getOldPassword().length()<6){
			errors.rejectValue("oldPassword", PASSWORD_TOO_SHORT);
		}
		if(form.getNewPassword().length()<6){
			errors.rejectValue("newPassword", PASSWORD_TOO_SHORT);
		}
		if(!form.getNewPassword().equals(form.getNewPasswordConfirm())){
			errors.rejectValue("newPasswordConfirm", PASSWORDS_DO_NOT_MATCH);
		}
	}
}
