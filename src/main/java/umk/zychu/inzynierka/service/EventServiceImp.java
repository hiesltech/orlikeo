package umk.zychu.inzynierka.service;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventForm;
import umk.zychu.inzynierka.controller.DTObeans.RegisterEventUser;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.controller.util.EventType;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.repository.EventDaoRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class EventServiceImp implements EventService {

	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(EventServiceImp.class);
	@Autowired
	EventDaoRepository eventDAO;
	@Autowired
	UserEventService userEventService;
	@Autowired
	GraphicService graphicService;
	@Autowired
	UserService userService;
	@Autowired
	UserEventDecisionService userEventDecisionService;
	@Autowired
	UserEventRoleService userEventRoleService;
	@Autowired
	EventStateService eventStateService;
	@Autowired
	FriendshipService friendshipService;
	@Autowired
	EventToApproveService eventToApproveService;
	@Autowired
	UserNotificationsService userNotificationsService;
	
	//next 48hrs
	private final Date incomingEventsDateInterval = new Date((new Date()).getTime() + 172400000);

	@Override
	public void acceptEvent(Integer id) {
		Event event = eventDAO.findOne(id);
		Graphic graphic = event.getGraphic();
		Orlik orlik = graphic.getOrlik();
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		EventState readyToAccept = eventStateService.findOne(EventState.READY_TO_ACCEPT);
		if(orlik.getAnimator() !=  null && orlik.getAnimator().equals(user) && event.getState().equals(readyToAccept)) {
			changeConcurrentEvents(event);
			eventToApproveService.removeEventFromWaitingForCheckByManager(event);
			EventState approved = eventStateService.findOne(EventState.APPROVED);
			event.setState(approved);
			save(event);
			userNotificationsService.eventWonRaceForGraphic(event);
		}
	}


	@Override
	public void changeConcurrentEvents(Event event) {
		UserDecision invited = userEventDecisionService.findOne(UserDecision.INVITED);
		UserDecision accepted = userEventDecisionService.findOne(UserDecision.ACCEPTED);
		UserDecision rejected = userEventDecisionService.findOne(UserDecision.REJECTED);
		UserEventRole guestRole = userEventRoleService.findOne(UserEventRole.GUEST);
		EventState inBasket = eventStateService.findOne(EventState.IN_A_BASKET);
		EventState readyToAcceptState = eventStateService.findOne(EventState.READY_TO_ACCEPT);
		Graphic graphic = event.getGraphic();
		List<Event> events = graphic.getEvents().stream()
				.filter(e -> !e.equals(event))
				.collect(Collectors.toList());
		userNotificationsService.eventsLostRaceForGraphic(events);
		if (!events.isEmpty()) {
			for (Event e : events) {
				e.getUsersEvent()
						.stream()
						.filter(ue -> (ue.getDecision().equals(accepted) || ue.getDecision().equals(rejected)) 
								&& ue.getRole().equals(guestRole))
						.forEach((o) -> {
							o.setDecision(invited);
							userEventService.save(o);
						});
				e.setGraphic(null);
				if(e.getState().equals(readyToAcceptState)){
					eventToApproveService.removeEventFromWaitingForCheckByManager(e);
				}
				e.setState(inBasket);
				save(e);
			}
		}
	}

	@Override
	public List<Event> findAll() {
		return eventDAO.findAll();
	}

	@Override
	public RegisterEventForm generateRegisterEventForm(Event event) {
		List<UserEvent> usersEvents = event.getUsersEvent();
		List<User> friends = friendshipService.getFriendsByState(Friendship.ACCEPTED);
		List<RegisterEventUser> users = new ArrayList<RegisterEventUser>();

		for (UserEvent userEvent : usersEvents) {
			boolean decision = false;
			if (userEvent.getDecision().getId() != 4)
				decision = true;
			String inviterEmail = userEvent.getInviter() != null ? userEvent
					.getInviter().getEmail() : null;
			RegisterEventUser e = new RegisterEventUser(userEvent.getUser()
					.getId(), userEvent.getUserPermission(), decision,
					userEvent.getUser().getEmail(), userEvent.getUser()
							.getDateOfBirth(), userEvent.getUser()
							.getPosition(), inviterEmail);
			users.add(e); 
		}
		for(User friend : friends){
			boolean isInvited = false;
			String inviterEmail = null;
			for(UserEvent userEvent : usersEvents){
				if(friend.getId() == userEvent.getUser().getId()){
					isInvited = true;
					inviterEmail = userEvent.getInviter() != null ? userEvent.getInviter().getEmail() : null;
				}
			}		
			if(isInvited == false){
				RegisterEventUser e1 = new RegisterEventUser(friend.getId(), 
						false, 
						false, 
						friend.getEmail(), 
						friend.getDateOfBirth(), 
						friend.getPosition(), inviterEmail);
				users.add(e1);
			}
		}
		return new RegisterEventForm(event.getId(), users);
	}
	
	private UserGameDetails generateUserGameDetails(UserEvent userEvent) {
		UserDecision accept = userEventDecisionService.findOne(UserDecision.ACCEPTED);
		UserDecision not_invited = userEventDecisionService.findOne(UserDecision.NOT_INVITED);
		Event event = userEvent.getEvent();
		Integer orlikId = 0;
		String address = "";
		String city = "";
		Date startDate = null;
		Date endDate = null;
		Boolean lights = false, water = false, shower = false;
		String shoes = "-";
		if(event.getGraphic() != null){
			Graphic graphic = event.getGraphic();
			startDate = graphic.getStartTime();
			endDate = graphic.getEndTime();
			Orlik orlik = graphic.getOrlik();
			orlikId = orlik.getId();
			address = orlik.getAddress();
			city = orlik.getCity();
			lights = orlik.getLights();
			water = orlik.getWater();
			shower = orlik.getShower();
			shoes = orlik.getShoes();
		}
		Integer eventId = event.getId();
		Integer stateId = event.getState().getId();
		String organizerEmail = event.getUserOrganizer().getEmail();
		Integer decisionId = userEvent.getDecision().getId();
		Integer roleId = userEvent.getRole().getId();
		Boolean permission = userEvent.getUserPermission();
		long willCome = event.getUsersEvent().stream()
								.filter(ue-> ue.getDecision().equals(accept))
								.count();
		int playersLimit = event.getPlayersLimit();
		long invited = event.getUsersEvent().stream()
								.filter(ue -> !ue.getDecision().equals(not_invited))
								.count();
		return  new UserGameDetails(
				eventId, 
				stateId, 
				orlikId,
				address, 
				city,
				organizerEmail,
				startDate,
				endDate,
				decisionId,
				roleId,
				permission,
				playersLimit, 
				willCome,
				invited,
				lights,
				water,
				shower,
				shoes);
	}

	@Override
	public List<UserGameDetails> generateUserGameDetailsList(List<UserEvent> userEvents) {
		List<UserGameDetails> userGamesDetailsList = new LinkedList<UserGameDetails>();
		Iterator<UserEvent> it = userEvents.iterator();
		while(it.hasNext()){
			UserEvent userEvent = it.next();
			UserGameDetails userGameDetails = generateUserGameDetails(userEvent);
			userGamesDetailsList.add(userGameDetails);
		}
		return userGamesDetailsList;
	}

	private EventWindowBlock getBlock(EventState state, UserEventRole role,
			Boolean incoming) {
		User user = userService.getUser(SecurityContextHolder.getContext()
				.getAuthentication().getName());
		return generateBlock(user, state, role, incoming);
	}

    private EventWindowBlock generateBlock(User user, EventState state, UserEventRole role, Boolean incoming){
        List<UserEvent> userEvents = new ArrayList<UserEvent>();
        userEvents.addAll(user.getUserEvents());
        if (state != null)
            userEvents.removeIf(ue -> !ue.getEvent().getState().equals(state));
        if (role != null)
            userEvents.removeIf(ue -> !ue.getRole().equals(role));
        if (incoming) {
            Date now = new Date();
            Date endDate = incomingEventsDateInterval;
            Date todayAndTomorrow = new Date(endDate.getYear(),
                    endDate.getMonth(), endDate.getDate());
            userEvents.removeIf(ue -> ue.getEvent().getGraphic()
                    .getStartTime().after(todayAndTomorrow));
        }
        Collections.sort(userEvents, new Comparator<UserEvent>() {
            @Override
            public int compare(UserEvent lhs, UserEvent rhs) {
                // return 1 if rhs should be before lhs
                // return -1 if lhs should be before rhs
                // return 0 otherwise
                Graphic lgraphic = lhs.getEvent().getGraphic();
                Graphic rgraphic = rhs.getEvent().getGraphic();
                if(lgraphic == null || rgraphic == null){
                    if(lgraphic == null && rgraphic == null)
                        return 0;
                    else if(lgraphic == null)
                        return -1;
                    else
                        return 1;
                }
                if (rgraphic.getStartTime()
                        .before(lgraphic.getStartTime()))
                    return 1;
                else if (lgraphic.getStartTime()
                        .before(rgraphic.getStartTime()))
                    return -1;
                else
                    return 0;
            }
        });
        Event event = !userEvents.isEmpty() ? userEvents.get(0).getEvent() : null;
        Graphic graphic = event != null ? event.getGraphic() : null;
        Orlik orlik = graphic != null ? graphic.getOrlik() : null;
        UserDecision decision = userEventDecisionService.findOne(UserDecision.ACCEPTED);
        long goingToCome = event != null ? event.getUsersEvent().stream()
                .filter(ue -> ue.getDecision().equals(decision)).count() : 0;
        String address = orlik != null ? orlik.getAddress() : "";
        String city = orlik != null ? orlik.getCity() : "";
        Date startTime = graphic != null ? graphic.getStartTime() : null;
        Date endTime = graphic != null ? graphic.getEndTime() : null;
        Integer playersLimit = event != null ? event.getPlayersLimit() : 0;
        Integer size = userEvents.size();
        String label;
        Integer displayOrder;
        switch (state.getId()) {
            case 1:
                label = "Kosz";
                displayOrder = 0;
                address = "brak orlika";
                break;
            case 2:
                label = "W budowie";
                displayOrder = 1;
                break;
            case 3:
                label = "Do akceptacji";
                displayOrder = 2;
                break;
            case 4:
                label = "Zagrożony";
                displayOrder = 3;
                break;
            case 5:
                if (!incoming) {
                    label = "Przyjęty";
                    displayOrder = 4;
                } else {
                    label = "Nadchodzący";
                    displayOrder = 5;
                }
                break;
            default:
                label = "";
                displayOrder = -1;
                break;
        }

        EventWindowBlock block = new EventWindowBlock(label, displayOrder,
                state.getId(), address, city, startTime, endTime, playersLimit,
                goingToCome, size, incoming);
        return block;
    }

	@Override
	public Optional<Event> getEventById(Integer id) {
		eventDAO.findOne(id);
		return Optional.ofNullable(eventDAO.findOne(id));
	}

	@Override
	public User getEventOrganizerUser(Event event) {
		UserEventRole role = userEventRoleService.findOne(UserEventRole.ORGANIZER);
		Optional<User> eventOrganizerOpt = event.getUsersEvent().stream()
			.filter(ue -> ue.getRole().equals(role) 
					&& ue.getEvent().equals(event))
			.map(ue ->ue.getUser())
			.findFirst();
		if(eventOrganizerOpt.isPresent()){
			return eventOrganizerOpt.get();
		}
		else{
			return null;
		}
	}

	@Override
	public List<EventWindowBlock> getEventWindowBlocks(UserEventRole role) {
		List<EventWindowBlock> windowBlocks = new ArrayList<EventWindowBlock>();
		List<EventState> states = eventStateService.findAll();
		for (EventState state : states) {
			windowBlocks.add(getBlock(state, role, false));
		}
		windowBlocks.add(getBlock(eventStateService.findOne(EventState.APPROVED), role, true));
		Collections.sort(windowBlocks, new Comparator<EventWindowBlock>(){
			@Override
			public int compare(EventWindowBlock lhs, EventWindowBlock rhs){
				return rhs.getDisplayOrder() - lhs.getDisplayOrder();
			}
		});
		return windowBlocks;
	}

    @Override
    public List<EventWindowBlock> getEventWindowBlocks(String username, UserEventRole role) {
        List<EventWindowBlock> windowBlocks = new ArrayList<EventWindowBlock>();
        List<EventState> states = eventStateService.findAll();
        for (EventState state : states) {
            windowBlocks.add(getBlock(username, state, role, false));
        }
        windowBlocks.add(getBlock(username, eventStateService.findOne(EventState.APPROVED), role, true));
        Collections.sort(windowBlocks, new Comparator<EventWindowBlock>() {
			@Override
			public int compare(EventWindowBlock lhs, EventWindowBlock rhs) {
				return rhs.getDisplayOrder() - lhs.getDisplayOrder();
			}
		});
        return windowBlocks;
    }

    private EventWindowBlock getBlock(String username, EventState state, UserEventRole role, boolean incoming) {
        User user = userService.getUser(username);
        return generateBlock(user, state, role, incoming);
    }

    @Override
	public UserGameDetails getGameDetails(Event event) {	
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		UserEvent userEvent = user.getUserEvents().stream()
			.filter(ue ->ue.getEvent().equals(event))
			.findFirst()
			.get();
		return	generateUserGameDetails(userEvent);
	}
	
	@Override
	public List<UserGameDetails> getGamesDetails(String username,
			EventType type, Integer stateId) {
		User user = userService.getUser(username);
		UserEventRole role = null;
		if (type != null) {
			switch (type) {
			case INVITATIONS:
				role = userEventRoleService.findOne(UserEventRole.GUEST);
				break;
			case ORGANIZED:
				role = userEventRoleService.findOne(UserEventRole.ORGANIZER);
				break;
			default:
				role = null;
				break;
			}
		}
		EventState state = stateId == null ? null : stateId == 5
				|| stateId == 6 ? eventStateService
				.findOne(EventState.APPROVED) : eventStateService
				.findOne(stateId);
		List<UserEvent> userEvents = user.getUserEvents();
		Date endDate = incomingEventsDateInterval;
		Date todayAndTomorrow = new Date(endDate.getYear(),
				endDate.getMonth(), endDate.getDate());
		if(stateId != null && stateId == 6){
			userEvents.removeIf(ue -> ue.getEvent().getGraphic().getStartTime().after(todayAndTomorrow));
		}
		if (role != null) {
			UserEventRole efectiveFinalRole = role;
			userEvents = userEvents.stream()
					.filter(ue -> ue.getRole().equals(efectiveFinalRole))
					.collect(Collectors.toList());
		}
		if (state != null) {
			EventState efectiveFinalState = state;
			userEvents = userEvents
					.stream()
					.filter(ue -> ue.getEvent().getState()
							.equals(efectiveFinalState))
					.collect(Collectors.toList());
		}
		List<UserGameDetails> userGameDetails = generateUserGameDetailsList(userEvents);
			userGameDetails.stream().forEach(gd -> {
				if (gd.getStateId() == 5 && gd.getStartDate().before(endDate)) {
					gd.setStateId(6);
				}
			});
		Collections.sort(userGameDetails, new Comparator<UserGameDetails>() {
			@Override
			public int compare(UserGameDetails o1, UserGameDetails o2) {
				// return 1 if rhs should be before lhs
				// return -1 if lhs should be before rhs
				// return 0 otherwise
				Date ldate = o1.getStartDate();
				Date rdate = o2.getStartDate();
				if(ldate == null || rdate == null){
					if(ldate == null && rdate == null)
						return 0;
					else if(ldate == null)
						return 1;
					else
						return -1;
				}
				if (rdate.before(ldate))
					return 1;
				else if (ldate.before(rdate))
					return -1;
				else
					return 0;
			}
		});
		return new ArrayList<UserGameDetails>(userGameDetails);
	}

	@Override
	public Boolean isEventMember(Event event) {
		String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
			User user = userService.getUser(userEmail);
		Optional<UserEvent> userEventOpt = user.getUserEvents().stream()
			.filter(ue ->ue.getEvent().equals(event))
			.findFirst();
		
		if(userEventOpt.isPresent()){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public Event registerEventForm(RegisterEventForm form) {
		try {
			User userOrganizer = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
			Graphic graphic = graphicService.findOne(form.getGraphicId());
			EventState state = eventStateService.findOne(EventState.IN_PROGRESS);
			Integer playersLimit = form.getUsersLimit();
			Event event = new Event(userOrganizer, graphic, state, playersLimit);
			UserEventRole organizerRole = userEventRoleService.findOne(UserEventRole.ORGANIZER);
			UserDecision organizerDecision = userEventDecisionService.findOne(UserDecision.ACCEPTED);
			UserEvent organizerUserEvent = new UserEvent(userOrganizer, organizerRole, organizerDecision, true, event, null);
			List<UserEvent> usersEvents = new LinkedList<UserEvent>();
			usersEvents.add(organizerUserEvent);
			ArrayList<RegisterEventUser> regUsersList = form.getEventFormMembers() != null ? (ArrayList<RegisterEventUser>)form.getEventFormMembers() : new ArrayList<>();
			for (RegisterEventUser regEventUser : regUsersList) {
				if (regEventUser.getAllowed() || regEventUser.getInvited()) {
					User userTarget = userService.getUser(regEventUser.getEmail());
					UserDecision decision = (regEventUser.getInvited()) ? userEventDecisionService
							.findOne(UserDecision.INVITED)
							: userEventDecisionService.findOne(UserDecision.NOT_INVITED);
					UserEventRole role = userEventRoleService.findOne(UserEventRole.GUEST);
					Boolean permission = regEventUser.getAllowed();
					UserEvent ue = new UserEvent(userTarget, role, decision, permission, event, userOrganizer);
					usersEvents.add(ue);

				}
			}
			event.setUsersEvent(usersEvents);
			event = save(event);
			userNotificationsService.eventCreated(event);
			return event;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Event save(Event event){
		return eventDAO.saveAndFlush(event);
	}

	@Override
	public void updateEvent(RegisterEventForm form) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		Event event = eventDAO.findOne(form.getEventId());
		// remove userOrganizer && editing user
		form.getEventFormMembers()
			.removeIf(reu -> reu.getEmail().equals(event.getUserOrganizer().getEmail()) || reu.getEmail().equals(user.getEmail())); 
		// remove all who's !invited && has !rights &&  !have userEvent
		form.getEventFormMembers()
			.removeIf(reu -> !reu.getAllowed() 
						&& !reu.getInvited() 
						&& !userEventService.findOne(event, userService.getUser(reu.getEmail()))
			.isPresent());
		if(!event.getUserOrganizer().equals(user)){
			form.getEventFormMembers()
				.removeIf(reu -> userEventService.findOne(event, userService.getUser(reu.getEmail())).isPresent() 
							&& !userEventService.findOne(event, userService.getUser(reu.getEmail())).get().getInviter().equals(user));
		}else{
			event.setPlayersLimit(form.getUsersLimit());
			save(event);
		}
		updateUsersEvents(form.getEventFormMembers(), event);
		userEventService.changeEventStateIfRequired(event);
		//TODO powiadomienia o dokonanych zmianach wobec zaproszonych/usuniętych itd.
	}

	@Override
	public void delete(Event e) {
		delete(e.getId());
	}

	@Override
	public Event findOne(Integer id) {
		return eventDAO.findOne(id);
	}

	@Override
	public void delete(Integer id) throws IllegalArgumentException{
		if(eventDAO.exists(id)){
			Event event = eventDAO.findOne(id);
			EventState readyToAccept = eventStateService.findOne(EventState.READY_TO_ACCEPT);
			if(event.getState().equals(readyToAccept)){
				eventToApproveService.removeEventFromWaitingForCheckByManager(event);
			}
			userNotificationsService.deleteAllOnEvent(event);
			userNotificationsService.eventIsRemovedByOrganizer(event);
			event.getUsersEvent().stream()
					.forEach(ue -> userEventService.delete(ue));
			eventDAO.delete(event);
		}
	}

	private void updateUsersEvents(List<RegisterEventUser> eventFormMembers, Event event) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		UserDecision invited = userEventDecisionService.findOne(UserDecision.INVITED);
		UserDecision notInvited = userEventDecisionService.findOne(UserDecision.NOT_INVITED);
		// are still event members
		eventFormMembers
				.stream()
				.filter(reu -> reu.getAllowed() || reu.getInvited())
				.forEach(
						reu -> {
							User u = userService.getUser(reu.getEmail());
							Optional<UserEvent> ueOpt = userEventService
									.findOne(event, u);
							if (ueOpt.isPresent()) {
								UserEvent ue = ueOpt.get();
								ue.setInviter(user);
								if (reu.getAllowed() && reu.getInvited()) {
									ue.setUserPermission(true);
									if(ue.getDecision().equals(notInvited)){
										ue.setDecision(invited);
									}
								} else if (!reu.getAllowed()
										&& reu.getInvited()) {
									ue.setUserPermission(false);
									if(ue.getDecision().equals(notInvited)){
										ue.setDecision(invited);
									};
								} else if (reu.getAllowed()
										&& !reu.getInvited()) {
									ue.setUserPermission(true);
									ue.setDecision(notInvited);
								}
								userEventService.save(ue);
							}
						});
		//are new to the event
		eventFormMembers.stream()
								.filter(reu -> (reu.getAllowed() || reu.getInvited())
										&& !userEventService.findOne(event, userService.getUser(reu.getEmail()))
										.isPresent())
								.forEach(reu -> {
									User u = userService.getUser(reu.getEmail());
									UserEventRole role = userEventRoleService.findOne(UserEventRole.GUEST);
									UserDecision decision = userEventDecisionService.findOne(UserDecision.NOT_INVITED);
									Boolean permission = false;
									if(reu.getAllowed() && reu.getInvited()){
										decision = userEventDecisionService.findOne(UserDecision.INVITED);
										permission = true;
									}else if(!reu.getAllowed() && reu.getInvited()){
										decision = userEventDecisionService.findOne(UserDecision.INVITED);
										permission = false;
									}else if(reu.getAllowed() && !reu.getInvited()){
										decision = userEventDecisionService.findOne(UserDecision.NOT_INVITED);
										permission = true;
									}
									UserEvent ue = new UserEvent (u, role, decision, permission, event, user);
									userEventService.save(ue);
								});
		// are no longer event members
		eventFormMembers
				.stream()
				.filter(reu -> !reu.getAllowed() && !reu.getInvited())
				.forEach(
						reu -> {
							User u = userService.getUser(reu.getEmail());
							Optional<UserEvent> ueOpt = userEventService
									.findOne(event, u);
							if (ueOpt.isPresent()) {
								UserEvent ue = ueOpt.get();
								event.getUsersEvent().remove(ue);
								save(event);
							}
						});
		Event ev = eventDAO.findOne(event.getId());	
	}
}
