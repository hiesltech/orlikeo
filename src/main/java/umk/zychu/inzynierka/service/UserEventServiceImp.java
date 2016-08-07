package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.repository.UserEventDaoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserEventServiceImp implements UserEventService {

	@Autowired
	UserEventDaoRepository userEventDAO;
	@Autowired
	EventService eventService;
	@Autowired
	EventStateService eventStateService;
	@Autowired
	UserService userService;
	@Autowired
	UserEventDecisionService userEventDecisionService;
	@Autowired
	UserNotificationsService userNotificationsService;
	
	@Autowired
	EventToApproveService eventToApproveService;
	
	private final int changeStatusBarrier = 3;

	@Override
	public UserEvent save(UserEvent event) {
		return userEventDAO.save(event);
	}

	@Override
	@Transactional
	public void setUserEventDecision(Event event, UserDecision decision) {
		User user = userService.getUser(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		event.getUsersEvent().stream().filter(ue -> ue.getUser().equals(user))
				.findFirst().ifPresent(ue -> {
					ue.setDecision(decision);
				});
		changeEventStateIfRequired(event);
	}
	
	@Override
	public void changeEventStateIfRequired(Event event) {
		EventState inProgress = eventStateService.findOne(EventState.IN_PROGRESS);
		EventState readyToAccept = eventStateService.findOne(EventState.READY_TO_ACCEPT);
		EventState approved = eventStateService.findOne(EventState.APPROVED);
		EventState threatened = eventStateService.findOne(EventState.THREATENED);
		UserDecision accepted = userEventDecisionService.findOne(UserDecision.ACCEPTED);
		EventState beforeState = event.getState();
		long counter = event.getUsersEvent().stream()
				.filter(ue -> ue.getDecision().equals(accepted))
				.count();
		if(event.getState().equals(inProgress) && counter >= changeStatusBarrier) {
			event.setState(readyToAccept);
			eventToApproveService.addEventToCheckByManager(event);
		}else if(event.getState().equals(readyToAccept) && counter < changeStatusBarrier){
			event.setState(inProgress);
			Optional<EventToApprove> optEventToApprove = eventToApproveService.findByEvent(event);
			if(optEventToApprove.isPresent()){
				EventToApprove evenToAprrove = optEventToApprove.get();
				eventToApproveService.delete(evenToAprrove);
			}
		}else if(event.getState().equals(approved) && counter < changeStatusBarrier){
			event.setState(threatened);
			//TODO set time to find player, if not change state to IN_PROGRESS
		}else if (event.getState().equals(threatened) && counter >= changeStatusBarrier){
			event.setState(approved);
		}
		if(!beforeState.equals(event.getState())){
			userNotificationsService.eventStateChanged(event);
		}
		eventService.save(event);
	}

	@Override
	public List<User> findUsersByEventAndDecision(Event event, UserDecision decision) {
		List<User> members = event.getUsersEvent().stream()
				.filter((x) -> x.getDecision().equals(decision))
				.map(ue -> ue.getUser())
				.collect(Collectors.toList());
		return members;
	}

	@Override
	public Optional<UserEvent> findOne(Event event, User user) {
		return event.getUsersEvent().stream()
				.filter(ue -> ue.getUser().equals(user))
				.findFirst();
	}

	@Override
	public List<User> findUsersByEventAndPermission(Event event,
			Boolean canInvite) {
		List<User> users = event.getUsersEvent().stream()
				.filter(ue -> ue.getUserPermission() == canInvite)
				.map(ue ->ue.getUser())
				.collect(Collectors.toList());
		return users;
	}

	@Override
	@Modifying
	public void delete(UserEvent userEvent) {
		UserEvent tmp = userEventDAO.findOne(userEvent.getId());
		System.out.println("equals? : " + tmp.equals(userEvent) + tmp.hashCode() + " : " + userEvent.hashCode());
		userEventDAO.delete(userEvent);
	}	
}
