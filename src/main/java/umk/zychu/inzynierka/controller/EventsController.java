package umk.zychu.inzynierka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class EventsController {

	

	@RequestMapping(value="/events/dupa", method = RequestMethod.GET)
	public String dupa2(ModelMap model) {

	    return "home";
	}
	
	
	@RequestMapping(value="/template", method = RequestMethod.GET)
	public String dupa(ModelMap model) {

	    return "home";
	}
	
	
	@RequestMapping( value="/login", method = RequestMethod.GET)
	public String create2(ModelMap model) {

		/*model.addAttribute("message", "Spring 3 MVC Hello World");*/
		return "profile";
	}

}