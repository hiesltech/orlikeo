package umk.zychu.inzynierka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import umk.zychu.inzynierka.controller.DTObeans.AcceptEventForm;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.EventService;
import umk.zychu.inzynierka.service.EventToApproveService;
import umk.zychu.inzynierka.service.OrlikService;
import umk.zychu.inzynierka.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;


@Controller
public class JavaplannerController {

	@Autowired
	private EventService eventService;
	@Autowired
	private EventToApproveService eventToApproveService;
	@Autowired
	private UserService userService;
	@Autowired
	private OrlikService orlikService;
	
	@RequestMapping("/planner")
	public String planner(HttpServletRequest request, ModelMap model)
			throws Exception {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		Optional<Orlik> orlikOpt = orlikService.getAnimatorOrlik(user);
		if(orlikOpt.isPresent()){
			model.addAttribute("orlik", orlikOpt.get().getId());
		}
		return "javaplanner2";
	}


	@RequestMapping("/pane")
	public String animatorPane(Map<String, Object> model){
		eventToApproveService.setNotificationsChecked();
		model.put("acceptEventForm", new AcceptEventForm());
		return "animatorPane";
	}
	
	 
	@RequestMapping(value="/pane/accept", method = RequestMethod.POST)
	public String acceptEvent(@RequestParam("ev") Integer id){
		System.out.println("event do zatwierdzenia " + id);
		eventService.acceptEvent(id);
		return "redirect:/pane";
		
	}
}











