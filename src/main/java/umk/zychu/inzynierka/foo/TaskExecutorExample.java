package umk.zychu.inzynierka.foo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.model.EventState;
import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.model.UserEventRole;
import umk.zychu.inzynierka.service.*;

import javax.annotation.PostConstruct;
import java.util.Date;

@Component
public class TaskExecutorExample {

	private TaskExecutor taskExecutor;
	private Date now = null;
	private long fromNow = 0;
	private long minute = 1000*60;
	private long halfAnHour = minute*30;
	private long quarterOfAnHour = minute*15;
	private EventState inBuild;
	private UserEventRole guestRole;
	private UserDecision notInvited;
	private UserDecision invited;
	private EventState inBasket;
	private EventState threatenedState;
	private EventState toApproveState;
	private EventState approvedState;


	@Autowired
    GraphicService graphicService;
	@Autowired
    EventStateService eventStateService;
	@Autowired
    EventService eventService;
	@Autowired
    UserEventRoleService userEventRoleService;
	@Autowired
    UserEventDecisionService userEventDecisionService;
	@Autowired
	UserEventService userEventService;
	@Autowired
	EventToApproveService eventToApproveService;


	public TaskExecutorExample() {
    	super();

    }

	TaskExecutorExample(TaskExecutor taskExecutor){
		super();
		this.taskExecutor = taskExecutor;
	}



	@PostConstruct
	private void post(){
		this.inBuild = eventStateService.findOne(EventState.IN_PROGRESS);
		this.guestRole = userEventRoleService.findOne(UserEventRole.GUEST);
		this.notInvited = userEventDecisionService.findOne(UserDecision.NOT_INVITED);
		this.invited = userEventDecisionService.findOne(UserDecision.INVITED);
		this.inBasket = eventStateService.findOne(EventState.IN_A_BASKET);
		this.threatenedState = eventStateService.findOne(EventState.THREATENED);
		this.toApproveState = eventStateService.findOne(EventState.READY_TO_ACCEPT);
		this.approvedState = eventStateService.findOne(EventState.APPROVED);
	}

	@Scheduled(fixedRate = 10000)
	public void printMessages() {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				now = new Date();
				fromNow = now.getTime();
				removeAllInBuildState();
				removeAllThreatenedAndToApprove();
				setGraphicAvailableForAllIfNotReserved();
				removeAllEventsOutOfTerm();
				clearGraphics();
			}
		});
	}
	@Transactional
	private void clearGraphics() {
		graphicService.findAll().stream()
				.filter(g -> fromNow - g.getEndTime().getTime() > halfAnHour + quarterOfAnHour)
				.forEach(g -> graphicService.delete(g));
	}

	@Transactional
	private void setGraphicAvailableForAllIfNotReserved() {
		if(graphicService.findAll().stream()
				.filter(g -> g.getAvailable() && g.getStartTime().getTime() - fromNow < quarterOfAnHour)
				.flatMap(g -> g.getEvents().stream())
				.filter(e -> e.getState().equals(approvedState)).count() == 0){
			graphicService.findAll().stream()
					.filter(g -> g.getAvailable() && g.getStartTime().getTime() - fromNow < quarterOfAnHour)
					.forEach(g -> {
						g.setAvailable(false);
						g.setTitle("Dostępny dla wszystkich (brak rezerwacji)");
						graphicService.save(g);
					});
		}

	}
	@Transactional
	private void removeAllEventsOutOfTerm() {
		graphicService.findAll().stream()
				.filter(g -> fromNow - g.getEndTime().getTime() > halfAnHour)
				.flatMap(g -> g.getEvents().stream())
				.forEach(e -> eventService.delete(e));
	}
	@Transactional
	private void removeAllInBuildState(){
        /*System.out.println("WORK TO DO");*/
		//reduce invitations
		graphicService.findAll().stream()
				.filter(g -> g.getStartTime().getTime() - fromNow < halfAnHour)
				.flatMap(g -> g.getEvents().stream())
				.filter(e -> e.getState().equals(inBuild))
				.flatMap(e -> e.getUsersEvent().stream())
				.filter(ue -> ue.getRole().equals(guestRole)
                				&& !ue.getDecision().equals(notInvited))
				.forEach(ue -> {
					ue.setDecision(invited);
					userEventService.save(ue);
				});
		//change events states
		graphicService.findAll().stream()
				.filter(g -> g.getStartTime().getTime() - fromNow < halfAnHour)
				.flatMap(g -> g.getEvents().stream())
				.filter(e -> e.getState().equals(inBuild))
				.forEach(e -> {
					e.setState(inBasket);
					e.setGraphic(null);
					eventService.save(e);
				});
		//disable reservations
		graphicService.findAll().stream()
				.filter(g -> g.getStartTime().getTime() - fromNow < halfAnHour)
				.forEach(g -> g.setAvailable(false));

	}
	@Transactional
	private void removeAllThreatenedAndToApprove() {
		graphicService.findAll().stream()
				.filter(g -> g.getStartTime().getTime() - fromNow < quarterOfAnHour)
				.flatMap(g ->g.getEvents().stream())
				.filter(e -> e.getState().equals(threatenedState) || e.getState().equals(toApproveState))
				.flatMap( e -> e.getUsersEvent().stream())
				.filter(ue -> ue.getRole().equals(guestRole)
						&& !ue.getDecision().equals(notInvited))
				.forEach(ue -> {
					ue.setDecision(invited);
					userEventService.save(ue);
				});
		graphicService.findAll().stream()
				.filter(g -> g.getStartTime().getTime() - fromNow < quarterOfAnHour)
				.flatMap(g ->g.getEvents().stream())
				.filter(e -> e.getState().equals(threatenedState) || e.getState().equals(toApproveState))
				.forEach(e -> {
					if(e.getState().equals(toApproveState)){
						eventToApproveService.removeEventFromWaitingForCheckByManager(e);
					}
					e.setState(inBasket);
					eventService.save(e);
				});
	}
}