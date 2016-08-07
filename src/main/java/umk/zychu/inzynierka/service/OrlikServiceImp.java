package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.controller.DTObeans.OrlikForm;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.model.UserNotification;
import umk.zychu.inzynierka.repository.OrlikDaoRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrlikServiceImp implements OrlikService {

	@Autowired
	OrlikDaoRepository orlikDAO;
	@Autowired
	UserService userService;
	@Autowired
	EventService eventService;
	@Autowired
	UserNotificationsService userNotificationsService;

	@Override
	public Orlik getOrlikById(Integer id) {
		return orlikDAO.findOne(id);
	}

	@Override
	public Orlik saveOrlik(Orlik orlik) {
		return orlikDAO.save(orlik);
	}

	public Map<String, String> getOrliksIdsAndNames() {
		Map<String, String> map = new LinkedHashMap<String, String>();
		map.put("0", "Wybierz orlik...");
		List<Orlik> orliks = orlikDAO.findAll().stream()
				.filter(o -> o.getAnimator() != null)
				.collect(Collectors.toList());
		orliks.forEach(o -> map.put(o.getId().toString(), o.getAddress()));
		return map;
	}

/*
	@Override
	public List<User> getOrlikManagersByOrlik(Orlik orlik) {
		return orlik.getOrlikManagers();
	}
*/

	@Override
	public List<UserGameDetails> getAllByManager(String username) {
		User manager = userService.getUser(username);
		Optional<Orlik> optOrlik = orlikDAO.findAll().stream()
				.filter(o -> o.getAnimator() != null && o.getAnimator().equals(manager))
				.findFirst();
		if (optOrlik.isPresent()) {
			Orlik orlik = optOrlik.get();
			List<UserEvent> organizersUserEvents = eventService
					.findAll()
					.stream()
					.filter(e -> e.getGraphic() != null && e.getGraphic().getOrlik().equals(orlik))
					//.peek(e -> System.out.println(e.getId()))
					.flatMap(e -> e.getUsersEvent().stream())
					.filter(ue -> ue.getUser().equals(
							ue.getEvent().getUserOrganizer()))
					.sorted((a, b) -> {
						if (a.getEvent()
								.getGraphic()
								.getStartTime()
								.before(b.getEvent().getGraphic()
										.getStartTime()))
							return -1;
						else if (a
								.getEvent()
								.getGraphic()
								.getStartTime()
								.after(b.getEvent().getGraphic().getStartTime()))
							return 1;
						else if (a.getEvent().getCreationDate()
								.before(b.getEvent().getCreationDate()))
							return -1;
						else if (a.getEvent().getCreationDate()
								.after(b.getEvent().getCreationDate()))
							return 1;
						else
							return 0;
					})
					//.peek(ue -> System.out.println(ue.getId()))
					.collect(Collectors.toList());
			return eventService
					.generateUserGameDetailsList(organizersUserEvents);
		} else
			return new ArrayList<UserGameDetails>();
	}

	@Override
	public List<Orlik> findAll() {
		return orlikDAO.findAll();
	}

	@Override
	@Transactional
	public void delete(Orlik orlik) {
		Set<User> usersForNotify = orlik.getGraphicCollection().stream()
				.flatMap(g -> g.getEvents().stream())
				.flatMap(e -> e.getUsersEvent().stream())
				.map(ue -> ue.getUser())
				.collect(Collectors.toSet());
		String city = orlik.getCity();
		String address = orlik.getAddress();
		orlik.getGraphicCollection().stream()
				.flatMap(g -> g.getEvents().stream())
				.forEach(e -> {
							userNotificationsService.deleteAllOnEvent(e);
							eventService.delete(e);
						}
				);
		orlikDAO.delete(orlik);
		usersForNotify.stream()
				.forEach(u -> {
					UserNotification un = new UserNotification("Usunięto orlik "
							+ city + " " + address
							+  " z systemu",
							"Wszystkie wydarzenia związane z tym obiektem zostały usunięte",
							u);
					userNotificationsService.save(un);
				});
	}

	@Override
	public Optional<Orlik> getAnimatorOrlik(User user) {
		return orlikDAO.findAll().stream()
				.filter(o -> o.getAnimator() != null && o.getAnimator().equals(user))
				.findFirst();
	}

	@Override
	public Boolean isOrlikManager(User user) {
		if(orlikDAO.findAll().stream()
				.filter(o -> o.getAnimator() != null && o.getAnimator().equals(user))
				.count() > 0)
			return true;
		return false;
	}

	@Override
	@Transactional
	public OrlikForm saveOrUpdateOrlik(OrlikForm form) {
		Orlik orlik;
		if(form.getId() != null) {
			orlik = getOrlikById(form.getId());
		}else {
			orlik = new Orlik();
		}
		orlik.setAddress(form.getAddress());
		orlik.setCity(form.getCity());
		orlik.setLights(form.getLights());
		orlik.setWater(form.getWater());
		orlik.setShower(form.getShower());
		orlik.setShoes(form.getShoes());
		User animator = null;
		if(form.getAnimatorEmail() != null)
			animator = userService.getUser(form.getAnimatorEmail());
		orlik.setAnimator(animator);
		orlik = saveOrlik(orlik);
		form.setId(orlik.getId());
		return form;
	}
}
