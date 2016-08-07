package umk.zychu.inzynierka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import umk.zychu.inzynierka.controller.DTObeans.RegisterUserBean;
import umk.zychu.inzynierka.controller.validator.RegisterUserBeanValidator;
import umk.zychu.inzynierka.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.Map;

@Controller
@RequestMapping("/register")
public class RegisterController {
	
	private static final Logger logger = LoggerFactory.getLogger(RegisterController.class);
	
	@Autowired
	private UserService userService;
	@Autowired
	private RegisterUserBeanValidator registerUserBeanValidator;
	
	@InitBinder("registerUserBean")
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(registerUserBeanValidator);
    }

	@RequestMapping(method=RequestMethod.GET)
	public String initForm(Locale locale, Map<String, Object> model){
		RegisterUserBean registerUserBean = new RegisterUserBean();
		model.put("registerUserBean", registerUserBean);
		return "register";
	}

	@RequestMapping(value="/new_user", method=RequestMethod.POST)
	public String processRegister(@ModelAttribute @Valid RegisterUserBean registerUserBean, BindingResult result, HttpServletRequest request ){		
		if(result.hasErrors()){
			return "register";
		}
		else{
			logger.debug("New user registered");
			userService.createNewUser(registerUserBean);
			return "redirect:/login";
		}		
	}
}
