package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.model.Event;
import umk.zychu.inzynierka.model.EventToApprove;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.repository.EventToApproveDaoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class EventToApproveServiceImp implements EventToApproveService {

	@Autowired
	private EventToApproveDaoRepository eventToApproveDAO;
	@Autowired
	private UserService userService;

	@Override
	public EventToApprove save(EventToApprove eventToApprove) {
		// TODO Auto-generated method stub
		return eventToApproveDAO.save(eventToApprove);
	}

	@Override
	public List<EventToApprove> findAll() {
		// TODO Auto-generated method stub
		return eventToApproveDAO.findAll();
	}

	@Override
	public Optional<EventToApprove> findByEvent(Event event) {
		return eventToApproveDAO.findAll().stream().filter(eta -> eta.getEvent().equals(event)).findFirst();
	}

	@Override
	public void delete(EventToApprove evenToAprrove) {
		// TODO Auto-generated method stub
		eventToApproveDAO.delete(evenToAprrove);
	}

	@Override
	public EventToApprove addEventToCheckByManager(Event event) {
		return eventToApproveDAO.save(new EventToApprove(event));
	}

	@Override
	public void removeEventFromWaitingForCheckByManager(Event event) {
		Optional<EventToApprove> optEventToApprove = eventToApproveDAO.findAll().stream().filter(eta -> eta.getEvent().equals(event)).findFirst();
		if(optEventToApprove.isPresent()){
			EventToApprove eventToApprove = optEventToApprove.get();
			eventToApproveDAO.delete(eventToApprove);
		}
	}

	@Override
	public void setNotificationsChecked() {
		// TODO Auto-generated method stub
		String email = SecurityContextHolder.getContext().getAuthentication().getName();
		User manager = userService.getUser(email);
		eventToApproveDAO
				.findAll()
				.stream()
				.filter(eta -> eta.getEvent().getGraphic().getOrlik()
						.getAnimator() != null
						&& eta.getEvent().getGraphic().getOrlik()
						.getAnimator().equals(manager)
						&& !eta.isChecked())
				.forEach(eta -> {
					eta.setChecked(true);
					eventToApproveDAO.save(eta);
				}); 
	}
}
