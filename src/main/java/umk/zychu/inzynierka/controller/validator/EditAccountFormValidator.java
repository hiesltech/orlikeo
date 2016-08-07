package umk.zychu.inzynierka.controller.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import umk.zychu.inzynierka.controller.DTObeans.EditAccountForm;
import umk.zychu.inzynierka.service.UserService;

@Component
public class EditAccountFormValidator implements Validator{

	@Autowired 
	UserService userService;
	
	private static final String EMPTY_OR_WHITESPACES_AGE_WEIGHT_OR_HEIGHT = "web.account.edit.empty";
	private static final String NOT_AN_INTEGER_VALUE = "web.account.validation.birthdate.integerOnly";
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return EditAccountForm.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmpty(errors, "dateOfBirth", EMPTY_OR_WHITESPACES_AGE_WEIGHT_OR_HEIGHT);
		ValidationUtils.rejectIfEmpty(errors, "weight", EMPTY_OR_WHITESPACES_AGE_WEIGHT_OR_HEIGHT);
		ValidationUtils.rejectIfEmpty(errors, "height", EMPTY_OR_WHITESPACES_AGE_WEIGHT_OR_HEIGHT);		
		EditAccountForm form = (EditAccountForm)target;
		
		if (form.getHeight() < 0) {
			errors.rejectValue("height", NOT_AN_INTEGER_VALUE);
		    // its an integer
		}
		
		if (form.getWeight() < 0) {
			errors.rejectValue("weight", NOT_AN_INTEGER_VALUE);
		    // its an integer
		}
				
		if(String.valueOf(form.getWeight()).matches("[^0]")){
			errors.reject("weight", NOT_AN_INTEGER_VALUE);
		}
		
		if(String.valueOf(form.getHeight()).matches("[^0]")){
			errors.reject("height", NOT_AN_INTEGER_VALUE);
		}
	}

}
