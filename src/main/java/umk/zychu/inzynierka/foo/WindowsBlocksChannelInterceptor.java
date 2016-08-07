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
import umk.zychu.inzynierka.controller.DTObeans.EventWindowBlock;
import umk.zychu.inzynierka.model.UserEventRole;
import umk.zychu.inzynierka.service.*;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledFuture;

@Service
public class WindowsBlocksChannelInterceptor extends ChannelInterceptorAdapter {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(WindowsBlocksChannelInterceptor.class);

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

    private Map<String, Object> windowBlocksSession = new HashMap<String, Object>();
    private Map<String, ScheduledFuture> blocksSessions;
    private Map<String, String> showSessionsPageHeader;
    private TaskScheduler scheduler = new ConcurrentTaskScheduler();

	@SuppressWarnings("rawtypes")
	public WindowsBlocksChannelInterceptor() {
		// TODO Auto-generated constructor stub
        blocksSessions = new Hashtable<String, ScheduledFuture>();
        showSessionsPageHeader = new Hashtable<String, String>();
	}

    private void sendUserWindowBlocks(String username, String sessionId){
        UserEventRole userEventRole = null;
        String page = null;
        if(showSessionsPageHeader.containsKey(sessionId)){
            page = showSessionsPageHeader.get(sessionId);
        }
        if (page != null) {
            switch (page) {
                case "organized":
                    userEventRole = userEventRoleService.findOne(1);
                    break;
                case "invitations":
                    userEventRole = userEventRoleService.findOne(2);
                    break;
                default:
                    break;
            }
            List<EventWindowBlock> eventsWindowsBlocks = eventService
                    .getEventWindowBlocks(username, userEventRole);
            windowBlocksSession.put("blocks", eventsWindowsBlocks);
            template.convertAndSendToUser(username, "/blocks/get", windowBlocksSession);

        }
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
        switch (sha.getCommand()) {
		case CONNECT:
			LOGGER.debug("MY STOMP Connect [sessionId: " + sessionId + "]");
                if (sha.toNativeHeaderMap().containsKey("page"))
                    showSessionsPageHeader.put(sessionId, sha.getFirstNativeHeader("page"));

            break;
		case SUBSCRIBE:
			LOGGER.debug("MY STOMP SUBSCRIBE [sessionId: " + sessionId + "]");
            if (sha.getDestination().equals("/user/blocks/get")) {
                blocksSessions.put(sessionId,
                        scheduler.scheduleWithFixedDelay(new Runnable() {
                            @Override
                            public void run() {
                                sendUserWindowBlocks(sha.getUser()
                                        .getName(), sha.getSessionId());
                            }
                        }, 5000));
            }
			break;
		case CONNECTED:
			LOGGER.debug("MY STOMP Connected [sessionId: " + sessionId + "]");
			break;
		case DISCONNECT:
			LOGGER.debug("MY STOMP Disconnect [sessionId: " + sessionId + "]");
            blocksSessions.remove(sha.getSessionId());
            showSessionsPageHeader.remove(sha.getSessionId());
			break;
		case SEND:
			break;
		default:
			System.out.println("MY CANT TAKE AN ACTION");
			break;
		}
    }
}
