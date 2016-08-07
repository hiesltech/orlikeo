package umk.zychu.inzynierka.service;

import java.util.List;

import umk.zychu.inzynierka.model.EventState;

public interface EventStateService {
	EventState findOne(Integer id);
	List<EventState> findAll();
}
