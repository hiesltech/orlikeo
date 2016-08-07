package umk.zychu.inzynierka.controller.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import umk.zychu.inzynierka.controller.DTObeans.ChoosenOrlikBean;

@Component
public class ChoosenOrlikBeanValidator implements Validator {
	
	private static final String ORLIK_NOT_CHOOSED = "web.event.select.pitch";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return ChoosenOrlikBean.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "empty", ORLIK_NOT_CHOOSED);
		ChoosenOrlikBean rub = (ChoosenOrlikBean) target;
		if(rub.getId()<1){
			errors.rejectValue("empty", ORLIK_NOT_CHOOSED);
		}
	}
}
