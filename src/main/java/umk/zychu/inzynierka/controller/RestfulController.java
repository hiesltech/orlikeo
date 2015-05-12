package umk.zychu.inzynierka.controller;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import umk.zychu.inzynierka.controller.DTObeans.JsonTestObject;
import umk.zychu.inzynierka.controller.util.EventsManager;
import umk.zychu.inzynierka.service.EventXService;

@Component
@Controller
@RequestMapping("/api/rest/")
public class RestfulController {

	@Autowired
	EventXService service;

	@RequestMapping(value = "/json", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody JsonTestObject postJSON(
			@RequestBody final JsonTestObject object) {
		System.out.println(object.toString());
		return object;
	}

	@RequestMapping(value = "/json/{name}", method = RequestMethod.GET)
	public @ResponseBody JsonTestObject getJSON(@PathVariable String name) {

		JsonTestObject object = new JsonTestObject();
		object.setName(name);
		object.setSurname("nazwisko");

		return object;

	}

	// works correctly
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ResponseBody
	public String greetig(
			@RequestParam(value = "name", defaultValue = "World") String name) {
		return "GET : " + name;
	}

	@RequestMapping(value = "/post", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	public @ResponseBody JsonTestObject greetig2(
			@RequestBody MultiValueMap<String, String> object) {
		JsonTestObject json = new JsonTestObject(object.getFirst("name"),
				object.getFirst("surname"));
		return json;
	}

	// works correctly : http://localhost:8080/jbossews/events?editing=false
	@RequestMapping(value = "/events", method = RequestMethod.GET)
	@ResponseBody
	public String events(
			@RequestParam(value = "editing", required = false) Boolean ed,
			HttpServletRequest request) {
		EventsManager evs = new EventsManager(request, service);
		System.out.println("/events: " + ed);
		return evs.run();

	}

	@RequestMapping(value = "/events", method = RequestMethod.POST, consumes = "application/x-www-form-urlencoded")
	@ResponseBody
	public String events2(@RequestParam("editing") Boolean editing,
			HttpServletRequest request) {
		EventsManager evs = new EventsManager(request, service);
		return evs.run();
	}

}