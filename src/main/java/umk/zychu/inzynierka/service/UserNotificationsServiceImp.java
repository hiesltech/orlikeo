package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.model.*;
import umk.zychu.inzynierka.repository.UserNotificationsDaoRepository;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by emagdnim on 2015-09-12.
 */
@Service
@Transactional
public class UserNotificationsServiceImp implements UserNotificationsService {

    @Autowired
    private UserNotificationsDaoRepository userNotificationsDAO;
    @Autowired
    private UserService userService;
    @Autowired
    UserEventDecisionService userEventDecisionService;
    SimpleDateFormat ftStart = new SimpleDateFormat("dd.mm.yyyy, hh:mm");
    SimpleDateFormat ftEnd = new SimpleDateFormat("hh:mm");
    UserDecision rejected;
    UserDecision notInvited;

    UserNotificationsServiceImp(){
        super();
    }

    UserNotificationsServiceImp(UserEventDecisionService userEventDecisionService){
        super();
        this.userEventDecisionService = userEventDecisionService;
        ftStart = new SimpleDateFormat("dd.mm.yyyy, hh:mm");
        ftEnd = new SimpleDateFormat("hh:mm");
        rejected = userEventDecisionService.findOne(UserDecision.REJECTED);
        notInvited = userEventDecisionService.findOne(UserDecision.NOT_INVITED);
    }


    @Override
    public List<UserNotification> findAll() {
        return userNotificationsDAO.findAll();
    }

    @Override
    public UserNotification findOne(Integer id) {
        return userNotificationsDAO.findOne(id);
    }

    @Override
    public void graphicChangedByAnimator(Graphic graphic) {
        String address = graphic.getOrlik().getAddress();
        String date = ftStart.format(graphic.getStartTime()) + " - " + ftEnd.format(graphic.getEndTime());
        graphic.getEvents().stream()
                .flatMap(e -> e.getUsersEvent().stream())
                .filter(ue -> !ue.getDecision().equals(notInvited))
                .forEach(ue -> {
                    String description = "Termin niedostępny!";
                    if(graphic.getAvailable()){
                        description = "Zmienione godziny wydarzenia: " + address + ", <br>" + date;
                    }
                    UserNotification notify = new UserNotification("Animator dokonał zmian w grafiku",
                            description,
                            ue);
                    save(notify);
                });
    }

    @Override
    public void eventIsRemovedByOrganizer(Event event) {
        if(event.getGraphic() != null) {
            Graphic graphic = event.getGraphic();
            String address = graphic.getOrlik().getAddress();
            String date = ftStart.format(event.getGraphic().getStartTime()) + " - " + ftEnd.format(event.getGraphic().getEndTime());
            event.getUsersEvent().stream()
                    .filter(ue -> ue.getInviter() != null && !ue.getDecision().equals(rejected))
                    .forEach(ue -> {
                        UserNotification notify = new UserNotification("Usunięto wydarzenie (" + event.getUserOrganizer().getEmail() + ")",
                                address + ", <br>" + date, ue.getUser());
                        save(notify);
                    });
        }
    }


    @Override
    public void eventStateChanged(Event event) {
        String stateName = "";
        switch (event.getState().getState()){
            case "in_basket" : stateName = "W koszu";
                break;
            case "in_progress" : stateName = "W budowie";
                break;
            case "ready_to_accept" : stateName = "Do akceptacji";
                break;
            case "threatened" : stateName = "Zagrożony";
                break;
            case "approved": stateName = "Przyjęty";
                break;
            default: break;
        }
        String subject = "Zmiana statusu wydarzenia  (" + stateName + ")";
        String description = "";
        if(event.getGraphic() != null){
            Graphic graphic = event.getGraphic();
            String address = graphic.getOrlik().getAddress();
            String date = ftStart.format(event.getGraphic().getStartTime()) + " - " + ftEnd.format(event.getGraphic().getEndTime());
            description = address + ", <br>" + date;
        }else{
            description = "Wydarzenie utraciło termin";
        }
        String efFinalDescription = description;
        event.getUsersEvent().stream()
                .filter(ue -> !ue.getDecision().equals(rejected)
                        && !ue.getDecision().equals(notInvited))
                .forEach(ue -> {
                    UserNotification notify = new UserNotification(subject, efFinalDescription, ue);
                    save(notify);
                });

    }

    @Override
    public void eventWonRaceForGraphic(Event event) {
        eventStateChanged(event);

    }

    @Override
    public void eventsLostRaceForGraphic(List<Event> events){
        if(!events.isEmpty()) {
            Graphic graphic = events.get(0).getGraphic();
            String address = graphic.getOrlik().getAddress();
            String date = ftStart.format(graphic.getStartTime()) + " - " + ftEnd.format(graphic.getEndTime());
            String subject = "Wydarzenie przegrało wyścig o termin";
            String description = address + ", <br>" + date + " został zajęty";
            events.stream()
                    .flatMap(e -> e.getUsersEvent().stream())
                    .filter(ue -> !ue.getDecision().equals(rejected)
                            && !ue.getDecision().equals(notInvited))
                    .forEach(ue -> {
                        UserNotification notify = new UserNotification(subject, description, ue);
                        save(notify);
                    });
        }
    }

    @Override
    public void save(UserNotification userNotification){
        userNotificationsDAO.save(userNotification);
    }

    @Override
    public void setCheckedForEvent(Event event) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUser(username);
        user.getUserNotifications().stream()
                .filter(un -> un.getLink().contains("event")
                        && un.getLink().contains(event.getId().toString()))
                .forEach(un -> {
                    un.setChecked(true);
                    save(un);
                });
    }

    @Override
    public void invitation(Friendship friendship) {
        String title = "Zaproszenie do znajomych!";
        String description = "Od użytkownika " + friendship.getActionUser().getEmail();
        User targetUser = friendship.getActionUser().equals(friendship.getFriendAccepter()) ? friendship.getFriendRequester() : friendship.getFriendAccepter();
        String href = "/friends/userDetail/" + friendship.getActionUser().getEmail();
        UserNotification notify = new UserNotification(title, description, href, targetUser);
        save(notify);
    }

    @Override
    public void acceptation(Friendship friendship) {
        String title = "Masz nowego znajomego!";
        String description = "Użytkownika " + friendship.getActionUser().getEmail() + " zaaceptował Twoje zaproszenie.";
        User targetUser = friendship.getActionUser().equals(friendship.getFriendAccepter()) ? friendship.getFriendRequester() : friendship.getFriendAccepter();
        String href = "/friends/userDetail/" + friendship.getActionUser().getEmail();
        UserNotification notify = new UserNotification(title, description, href, targetUser);
        save(notify);
    }

    @Override
    public void denial(Friendship friendship) {
        String title = "Twoje zaproszenie zostało odrzucone!";
        String description = "Użytkownik " + friendship.getActionUser().getEmail() + " odrzucił zaproszenie.";
        User targetUser = friendship.getActionUser().equals(friendship.getFriendAccepter()) ? friendship.getFriendRequester() : friendship.getFriendAccepter();
        String href = "/friends/userDetail/" + friendship.getActionUser().getEmail();
        UserNotification notify = new UserNotification(title, description, href, targetUser);
        save(notify);
    }

    @Override
    public void blocking(Friendship friendship) {
        String title = "Zostałeś zablokowany!";
        String description = "Użytkownik " + friendship.getActionUser().getEmail() + " zablokował Cię.";
        User targetUser = friendship.getActionUser().equals(friendship.getFriendAccepter()) ? friendship.getFriendRequester() : friendship.getFriendAccepter();
        String href = "/friends/userDetail/" + friendship.getActionUser().getEmail();
        UserNotification notify = new UserNotification(title, description, href, targetUser);
        save(notify);
    }

    @Override
    public void unblocking(Friendship friendship) {
        String title = "Zostałeś odblokowany!";
        String description = "Użytkownik " + friendship.getActionUser().getEmail() + " odblokował Cię.";
        User targetUser = friendship.getActionUser().equals(friendship.getFriendAccepter()) ? friendship.getFriendRequester() : friendship.getFriendAccepter();
        String href = "/friends/userDetail/" + friendship.getActionUser().getEmail();
        UserNotification notify = new UserNotification(title, description, href, targetUser);
        save(notify);
    }


    @Override
    public void cancelInvitation(User user, User userTarget){
        String title = "Cofnięto zaproszenie!";
        String description = "Użytkownik " + user.getEmail() + " wycofał zaproszenie.";
        User targetUsr = userTarget;
        UserNotification notify = new UserNotification(title, description, null, targetUsr);
        save(notify);
    }

    @Override
    public void deleteAllWithFriend(User friend) {
        User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
        user.getUserNotifications().stream()
                .filter(un -> un.getLink().contains(friend.getEmail()))
                .forEach( un -> {
                    un.setChecked(true);
                    userNotificationsDAO.save(un);
                });
    }

    @Override
    public void remove(User actionUser, User targetUser) {
        String title = "Usunięta znajomość!";
        String description = "Użytkownik " + actionUser.getEmail() + " usunął Ciebie ze znajomych.";
        User targetUsr = targetUser;
        UserNotification notify = new UserNotification(title, description, null, targetUsr);
        save(notify);
    }

    @Override
    public void deleteAllOnEvent(Event event) {
        List<User> users = event.getUsersEvent().stream()
                .map(ue -> ue.getUser())
                .collect(Collectors.toList());
        Set<UserNotification> notifications = users.stream()
                .peek(u -> System.out.println(u.getUserNotifications()))
                .flatMap(u -> u.getUserNotifications().stream())
                .collect(Collectors.toSet());

        notifications = notifications.stream()
                .filter(un -> un.getLink().contains("event")
                        && un.getLink().contains(event.getId().toString()))
                .collect(Collectors.toSet());
        notifications.stream()
                .forEach(un -> userNotificationsDAO.delete(un));
    }

    @Override
    public void eventCreated(Event event) {
        Graphic graphic = event.getGraphic();
        String address = graphic.getOrlik().getAddress();
        String date = ftStart.format(graphic.getStartTime()) + " - " + ftEnd.format(graphic.getEndTime());
        String subject = "Użytkownik " + event.getUserOrganizer().getEmail() + " utworzył wydarzenie";
        String description = address + ", <br>" + date;
        event.getUsersEvent().stream()
                .filter(ue -> ue.getInviter() != null)
                .forEach(ue -> {
                    UserNotification notify = new UserNotification(subject, description, ue);
                    save(notify);
                });
    }

    @Override
    public void eventGraphicChangedByOrganizer(Event event) {
        Graphic graphic = event.getGraphic();
        String address = graphic.getOrlik().getAddress();
        String date = ftStart.format(graphic.getStartTime()) + " - " + ftEnd.format(graphic.getEndTime());
        String subject = "Użytkownik " + event.getUserOrganizer().getEmail() + " zmienił miejsce wydarzenia";
        String description = address + ", <br>" + date;
        event.getUsersEvent().stream()
                .filter(ue -> ue.getInviter() != null)
                .forEach(ue -> {
                    UserNotification notify = new UserNotification(subject, description, ue);
                    save(notify);
                });
    }

    @Override
    public void delete(UserNotification userNotification){
        userNotificationsDAO.delete(userNotification);
    }
}
