package umk.zychu.inzynierka.foo;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.model.EventToApprove;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.service.*;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

@Service
public class AnimatorChannelInterceptor extends ChannelInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(AnimatorChannelInterceptor.class);

    @Autowired
	private SimpMessagingTemplate template;
	@Autowired
	private EventService eventService;
	@Autowired
	private EventStateService eventStateService;
	@Autowired
	private OrlikService orlikService;
	@Autowired
	private EventToApproveService eventToApproveService;
	@Autowired
	private UserService userService;
    @Autowired
    private UserEventRoleService userEventRoleService;


	private List<UserGameDetails> orlikGamesList = new ArrayList<UserGameDetails>();

	private Map<String, ScheduledFuture> eventsSessions;
	private Map<String, ScheduledFuture> notificationsSessions;

    private TaskScheduler scheduler = new ConcurrentTaskScheduler();

	@SuppressWarnings("rawtypes")
	public AnimatorChannelInterceptor() {
		// TODO Auto-generated constructor stub
		eventsSessions = new Hashtable<String, ScheduledFuture>();
		notificationsSessions = new Hashtable<String, ScheduledFuture>();
	}

	private void sendEventsToManager(String username) {
		orlikGamesList = orlikService.getAllByManager(username);
		template.convertAndSendToUser(username, "/events/read", orlikGamesList);
	}
	
	private void sendNotificationToManager(String username){
		User manager = userService.getUser(username);
		List<EventToApprove> eventsToApprove = eventToApproveService
				.findAll()
				.stream()
				.filter(eta -> eta.getEvent().getGraphic() != null
						&& eta.getEvent().getGraphic().getOrlik().getAnimator() != null
						&& eta.getEvent().getGraphic().getOrlik().getAnimator().equals(manager)
						&& !eta.isChecked())
				.collect(Collectors.toList());
		long counter = eventsToApprove.size();
	    template.convertAndSendToUser(username, "/notifications/read", counter);
	}

	@Override
	public void postSend(Message<?> message, MessageChannel channel,
			boolean sent) {
		StompHeaderAccessor sha = StompHeaderAccessor.wrap(message);
		// ignore non-STOMP messages like heartbeat messages
		if (sha.getCommand() == null) {
			System.out
					.println("ignored non-STOMP messages like heartbeat messages");
			return;
		}
		String sessionId = sha.getSessionId();
		System.out.println("session " + " " + sha.getSessionId()
				+ " and COMMAND IS " + sha.getCommand());
		System.out.println("user " + " " + sha.getUser().getName());
		System.out.println("SHA " + " " + sha.toString());
        switch (sha.getCommand()) {
		case CONNECT:
			LOGGER.debug("MY STOMP Connect [sessionId: " + sessionId + "]");
            break;
		case SUBSCRIBE:
			LOGGER.debug("MY STOMP SUBSCRIBE [sessionId: " + sessionId + "]");
			if (sha.getDestination().equals("/user/events/read")) {
				eventsSessions.put(sessionId,
						scheduler.scheduleWithFixedDelay(new Runnable() {
							@Override
							public void run() {
								sendEventsToManager(sha.getUser().getName());
							}
						}, 5000));
			}
			if (sha.getDestination().equals("/user/notifications/read")) {
				notificationsSessions.put(sessionId,
						scheduler.scheduleWithFixedDelay(new Runnable() {
							@Override
							public void run() {
								sendNotificationToManager(sha.getUser()
										.getName());
							}
						}, 5000));
			}
			break;
		case CONNECTED:
			LOGGER.debug("MY STOMP Connected [sessionId: " + sessionId + "]");
			System.out.println("MY CONNECTED sessionid: " + " " + sessionId);
			break;
		case DISCONNECT:
			LOGGER.debug("MY STOMP Disconnect [sessionId: " + sessionId + "]");
			System.out.println("MY DISCONNECT sessionid: " + " " + sessionId);
			eventsSessions.remove(sessionId);
            notificationsSessions.remove(sessionId);
			break;
		case SEND:
			System.out.println("Send command: " + " " + sessionId);
			break;
		default:
			System.out.println("MY CANT TAKE AN ACTION");
			break;
		}
	}
}