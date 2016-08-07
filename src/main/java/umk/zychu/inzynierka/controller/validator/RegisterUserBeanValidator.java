package umk.zychu.inzynierka.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import umk.zychu.inzynierka.controller.DTObeans.RegisterUserBean;
import umk.zychu.inzynierka.service.UserService;

@Component
public class RegisterUserBeanValidator implements Validator {
	
	@Autowired
	UserService userService;

	private static final String EMPTY_OR_WHITESPACE_EMAIL = "web.register.validation.email.required";
	private static final String EMAIL_IN_USE = "web.register.validation.email.in_use";
	private static final String INVALID_EMAIL = "web.register.validation.email.invalid";
	private static final String EMPTY_OR_WHITESPACE_PASSWORD = "web.register.validation.password.required";
	private static final String EMPTY_OR_WHITESPACE_REPEATED_PASSWORD = "web.register.validation.repeated_password.required";
	private static final String PASSWORDS_DO_NOT_MATCH = "web.register.validation.repeated_password.not_match";
	private static final String PASSWORD_TOO_SHORT = "web.register.validation.password.short";
	private static final String REGULATION_NOT_ACCEPTED = "web.register.validation.accept_regulation.required";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return RegisterUserBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", EMPTY_OR_WHITESPACE_EMAIL);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", EMPTY_OR_WHITESPACE_PASSWORD);
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", EMPTY_OR_WHITESPACE_REPEATED_PASSWORD);
		
		RegisterUserBean rub = (RegisterUserBean) target;
		
		if(userService.checkIfUserExists(rub.getEmail())){
			errors.rejectValue("email", EMAIL_IN_USE);
		}
		
		if(!rub.getEmail().matches("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}")){
			errors.rejectValue("email", INVALID_EMAIL);
		}
		
		if(rub.getPassword().length()<6){
			errors.rejectValue("password", PASSWORD_TOO_SHORT);
		}
		
		if(!rub.getPassword().equals(rub.getRepeatedPassword())){
			errors.rejectValue("repeatedPassword", PASSWORDS_DO_NOT_MATCH);
		}
		
		if(!rub.getAcceptRegulation()){
			errors.rejectValue("acceptRegulation", REGULATION_NOT_ACCEPTED);
		}	
	}
}
