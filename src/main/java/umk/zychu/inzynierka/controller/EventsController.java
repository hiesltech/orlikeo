package umk.zychu.inzynierka.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(value="/kontroler")
public class EventsController {

	

	@RequestMapping(method = RequestMethod.GET)
	public String dupa2(ModelMap model) {

	    return "home";
	}
	
	
	@RequestMapping(value="/metoda", method = RequestMethod.GET)
	public String dupa(ModelMap model) {

	    return "home";
	}


}