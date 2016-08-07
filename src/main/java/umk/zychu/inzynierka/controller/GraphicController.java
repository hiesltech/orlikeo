package umk.zychu.inzynierka.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import umk.zychu.inzynierka.controller.DTObeans.ChoosenOrlikBean;
import umk.zychu.inzynierka.controller.DTObeans.JsonEventObject;
import umk.zychu.inzynierka.model.EventState;
import umk.zychu.inzynierka.model.Graphic;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/graphic")
public class GraphicController {
	
	private static final Logger logger = LoggerFactory.getLogger(GraphicController.class);
	
	@Autowired
	private OrlikService orlikService;
	@Autowired
	private EventStateService eventStateService;

	@RequestMapping(method = RequestMethod.POST)
	public String graphic(
			@ModelAttribute @Valid ChoosenOrlikBean chooseOrlikBean,
			ModelMap model, BindingResult result, HttpServletRequest request) {
		String url;
		if (result.hasErrors() || chooseOrlikBean.getId() == 0) {
			url = "redirect:/events/create";
		}else{
			url = "redirect:graphic/" + chooseOrlikBean.getId();
		}
		if(chooseOrlikBean.getEventId() != null){
			url += "?event=" + chooseOrlikBean.getEventId();
		}
		return url;
	}

	@RequestMapping(value = "/{orlik}", method = RequestMethod.GET)
	public ModelAndView graphic(@PathVariable("orlik") Integer orlikId, @RequestParam(value="event", required = false) Integer eventId) {
		ObjectMapper mapper = new ObjectMapper();
		Orlik orlik = orlikService.getOrlikById(orlikId);
		List<Graphic> graphics = orlik.getGraphicCollection();
		List<JsonEventObject> graphicEntityList = new ArrayList<JsonEventObject>();
		for (Graphic i : graphics) {
			Graphic graphic = i;
			EventState approved = eventStateService.findOne(EventState.APPROVED);
            EventState treatened = eventStateService.findOne(EventState.THREATENED);
			if(graphic.getEvents().size() == 1 && (graphic.getEvents().get(0).getState().equals(approved) || graphic.getEvents().get(0).getState().equals(treatened))){
				graphic.setAvailable(false);
				graphic.setTitle("Rezerwacja: " + graphic.getEvents().get(0).getUserOrganizer().getEmail());
			}
			JsonEventObject jObj = new JsonEventObject(graphic);
			graphicEntityList.add(jObj);
		}
		ModelAndView model = new ModelAndView("graphic");
		String json = "";
		try {
			json = mapper.writeValueAsString(graphicEntityList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("list", json);
		model.addObject("orlikInfo", orlik);
		User animator = orlik.getAnimator();
		model.addObject("animator", animator);
		if(eventId != null)
			model.addObject("evId", eventId);
		return model;
	}
}