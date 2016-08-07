package umk.zychu.inzynierka.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.model.EventState;
import umk.zychu.inzynierka.repository.EventStateDaoRepository;

@Service
@Transactional
public class EventStateServiceImp implements EventStateService{

	@Autowired
	private EventStateDaoRepository eventStateDAO;
	
	@Override
	public EventState findOne(Integer id) {
		return eventStateDAO.findOne(id);
	}

	@Override
	public List<EventState> findAll() {
		return eventStateDAO.findAll();
	}
}
