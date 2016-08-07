package umk.zychu.inzynierka.controller;

import org.springframework.stereotype.Controller;


@Controller
public class WebsocketController {

/*	@Autowired
    AmqpTemplate amqpTemplate;*/
   /* @Autowired
    UserService userService;
    @Autowired
    EventService eventService;

	@RequestMapping("/websocket")
	public String websocket() {
		return "websocket";
	}

	@MessageMapping("/hello")
	@SendToUser("/queue/myqueue")
	public Greeting websocketInfo(HelloMessage message) throws Exception {
		System.out.println(("Hello, " + message.getName() + " " 	
				+ " !").toString());
		return new Greeting((String)amqpTemplate.receiveAndConvert("myqueue") + message.getName() + " oko");
	}

    @MessageMapping("/hello")
    @SendToUser("/notifications/read")
    public long websocketNotifications() throws Exception {
        Thread.sleep(3000); // simulated delay
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username);
        return eventService.findAll().stream().filter(e -> e.getUserOrganizer().equals(username)).count();
    }*/

/*	@RequestMapping("/websocket2")
	public String websocket2() {
		return "websocket2";
	}*/

}

/*@Service
class RabbitService {

	@Autowired
	AmqpTemplate amqpTemplate;

	@Autowired
    SimpMessageSendingOperations template;

	@Scheduled(fixedDelay = 3000)
	public void sendAMQP() {

		String queue = "/queue/myqueue";
		System.out.println("scheduled");
		template.convertAndSendToUser("maria@o2.pl", queue, "dupa");

		//System.out.println("convert and send");
		amqpTemplate.convertAndSend("myqueue", "foo");
		
		
		 String foo = (String) amqpTemplate.receiveAndConvert("myqueue");
		 System.out.print("FOO" + foo);
		 

	}
}*/
