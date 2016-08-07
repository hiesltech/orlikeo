package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.controller.util.EventType;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserEvent;
import umk.zychu.inzynierka.model.UserEventRole;

import java.util.List;
import java.util.Optional;

public interface EventService {
	void acceptEvent(Integer id);
	void delete(Integer id);

	void changeConcurrentEvents(Event event);

	List<Event> findAll();
	RegisterEventForm generateRegisterEventForm(Event event);
	List<UserGameDetails> generateUserGameDetailsList(List<UserEvent> userEvents);
	Optional<Event> getEventById(Integer id);
	User getEventOrganizerUser(Event event);
	List<EventWindowBlock> getEventWindowBlocks(UserEventRole role);
    List<EventWindowBlock> getEventWindowBlocks(String username, UserEventRole role);
	UserGameDetails getGameDetails(Event event);
	List<UserGameDetails> getGamesDetails(String username, EventType type, Integer stateId);
	Boolean isEventMember(Event event);
	Event registerEventForm(RegisterEventForm form);
	Event save(Event event);
	void updateEvent(RegisterEventForm form);
	void delete(Event e);
	Event findOne(Integer id);
}
