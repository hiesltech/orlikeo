package umk.zychu.inzynierka.controller.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import umk.zychu.inzynierka.repository.EventStateDaoRepository;
import umk.zychu.inzynierka.repository.UserEventDecisionDAOrepository;
import umk.zychu.inzynierka.service.*;


@Component
public class AllServices{

	@Autowired
	private GraphicService graphicService;
	@Autowired
	private EventService eventService;
	@Autowired
	private UserEventService userEventService;
	@Autowired
	private OrlikService orlikService;
	@Autowired
	private UserEventDecisionDAOrepository userEventDecisionDAO;
	@Autowired
	private EventStateDaoRepository stateDAO;
	@Autowired
	private UserEventRoleService userEventRoleService;
	@Autowired
	private EventToApproveService eventToApproveService;
    @Autowired
    private EventStateService eventStateService;

	public UserNotificationsService getUserNotificationsService() {
		return userNotificationsService;
	}

	public void setUserNotificationsService(UserNotificationsService userNotificationsService) {
		this.userNotificationsService = userNotificationsService;
	}

	@Autowired
	private UserNotificationsService userNotificationsService;

    public EventStateService getEventStateService(){
        return eventStateService;
    }

    public void setEventStateService(EventStateService eventStateService){
        this.eventStateService = eventStateService;
    }

    public EventToApproveService getEventToApproveService() {
		return eventToApproveService;
	}

	public void setEventToApproveService(EventToApproveService eventToApproveService) {
		this.eventToApproveService = eventToApproveService;
	}

	public UserEventRoleService getUserEventRoleService() {
		return userEventRoleService;
	}

	public void setUserEventRoleService(UserEventRoleService userEventRoleService) {
		this.userEventRoleService = userEventRoleService;
	}

	public UserEventService getUserEventService() {
		return userEventService;
	}

	public EventService getEventService() {
		return eventService;
	}

	public GraphicService getGraphicService() {
		return graphicService;
	}	
	
	public AllServices() {
		super();
	}

	/**
	 * @return the userEventDecisionDAO
	 */
	public UserEventDecisionDAOrepository getUserEventDecisionDAO() {
		return userEventDecisionDAO;
	}

	/**
	 * @param userEventDecisionDAO the userEventDecisionDAO to set
	 */
	public void setUserEventDecisionDAO(
			UserEventDecisionDAOrepository userEventDecisionDAO) {
		this.userEventDecisionDAO = userEventDecisionDAO;
	}

	/**
	 * @return the orlikService
	 */
	public OrlikService getOrlikService() {
		return orlikService;
	}

	/**
	 * @param orlikService the orlikService to set
	 */
	public void setOrlikService(OrlikService orlikService) {
		this.orlikService = orlikService;
	}

	/**
	 * @return the stateDAO
	 */
	public EventStateDaoRepository getStateDAO() {
		return stateDAO;
	}
	
}
