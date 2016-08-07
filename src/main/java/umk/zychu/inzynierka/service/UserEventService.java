package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.model.UserEvent;

import java.util.List;
import java.util.Optional;

public interface UserEventService {
	UserEvent save(UserEvent event);
	Optional<UserEvent> findOne(Event event, User user);
	List<User> findUsersByEventAndPermission(Event event, Boolean canInvite);
	List<User> findUsersByEventAndDecision(Event event, UserDecision decision);
	void setUserEventDecision(Event event, UserDecision decision);
	void delete(UserEvent userEvent);
	void changeEventStateIfRequired(Event event);
}
