package umk.zychu.inzynierka.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelpController {

	@RequestMapping(value = "/help", method = RequestMethod.GET)
	public ModelAndView help(ModelMap model) {
		model.addAttribute("message", "Spring 3 MVC Hello World");
		return new ModelAndView("help");

	}
}
