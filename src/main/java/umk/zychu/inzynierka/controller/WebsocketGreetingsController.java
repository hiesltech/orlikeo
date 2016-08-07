package umk.zychu.inzynierka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import umk.zychu.inzynierka.service.UserService;

/**
 * Created by emagdnim on 2015-09-04.
 */
@Controller
public class WebsocketGreetingsController {


    @Autowired
    UserService userService;


/*    @Autowired
    private SimpMessagingTemplate template;*/

    @Autowired
    private SimpMessageSendingOperations template;

    @RequestMapping("/websocket")
    public String websocket() {
        return "websocket";
    }


/*    @MessageMapping("/hello")
    @SendToUser("/topic/greetings")
    public Greeting greeting(HelloMessage message, Message message2) throws Exception {
        *//*Thread.sleep(3000); // simulated delay*//*
        System.out.println("MESSAGE: " + message2.toString());
        return new Greeting("Hello, " + message.getName() + "!");
    }*/

    @MessageMapping("/hello")
    @SendTo("/topic/price")
    public String greeting2() throws Exception {
        /*Thread.sleep(3000); // simulated delay*/
        /*System.out.println("dupa");
        template.convertAndSend("/topic/price", 666);*/
        return "dupa";
    }

/*    @Scheduled( fixedDelay = 5000)
    public void greeting() throws Exception {
        *//*Thread.sleep(3000); // simulated delay*//*
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        template.convertAndSendToUser(username, "/topic/greetings", username);
    }*/
}
