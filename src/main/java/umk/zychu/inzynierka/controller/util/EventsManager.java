package umk.zychu.inzynierka.controller.util;

import com.dhtmlx.planner.DHXEv;
import com.dhtmlx.planner.DHXEvent;
import com.dhtmlx.planner.DHXEventsManager;
import com.dhtmlx.planner.DHXStatus;
import umk.zychu.inzynierka.controller.DTObeans.DHXCustomEvent;
import umk.zychu.inzynierka.model.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;


public class EventsManager extends DHXEventsManager {

	private AllServices services;
	private Boolean allowReservation = false;	
	Orlik  orlik;

	public EventsManager(HttpServletRequest request, AllServices allServices) {
		super(request);
		this.services = allServices;	
		if(request.getParameter("allow") != null){
			allowReservation = Boolean.parseBoolean(request.getParameter("allow"));
		}
		if(request.getParameter("orlik") != null){
			Integer orlikId = Integer.parseInt(request.getParameter("orlik"));
			orlik = services.getOrlikService().getOrlikById(orlikId);
		}
	}
	
	public EventsManager(HttpServletRequest request) {
		super(request);
	}

	@Override
	public Iterable<DHXEv> getEvents() {
		ArrayList<DHXEv> dhxEvents = new ArrayList<DHXEv>();
		try {
            EventState approved = services.getEventStateService().findOne(EventState.APPROVED);
            EventState treatened = services.getEventStateService().findOne(EventState.THREATENED);
			Collection<Graphic> orlikGraphics = orlik.getGraphicCollection();
			Iterator<Graphic> i = orlikGraphics.iterator();
			while (i.hasNext()) {
				Graphic graphic = (Graphic) i.next();
/*                if(graphic.getEvents().size() == 1 && (graphic.getEvents().get(0).getState().equals(approved) || graphic.getEvents().get(0).getState().equals(treatened))){
                    *//*graphic.setAvailable(false);*//*
                    graphic.setTitle(graphic.getEvents().get(0).getUserOrganizer().getEmail());
                }*/
				DHXCustomEvent dhxEvent = new DHXCustomEvent(graphic);
				dhxEvents.add(dhxEvent);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dhxEvents;
	}
		
	@Override
	public DHXStatus saveEvent(DHXEv dhxEvent, DHXStatus dhxStatus) {
		try {
			if (dhxStatus == DHXStatus.UPDATE) {
				updateWithDependencies(dhxEvent);
			} else if (dhxStatus == DHXStatus.INSERT) {
				Graphic graphic = new Graphic(dhxEvent, orlik);
				graphic.setAvailable(allowReservation);
				services.getGraphicService().save(graphic);
			} else if (dhxStatus == DHXStatus.DELETE) {
				Graphic graphic = services.getGraphicService().findOne(dhxEvent.getId());
				services.getGraphicService().delete(graphic);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dhxStatus;
	}

	@Override
	public DHXEv createEvent(String id, DHXStatus status) {
		return new DHXEvent();
	}
	
	private void updateWithDependencies(DHXEv dhxEvent) {
		Graphic graphic = services.getGraphicService()
				.findOne(dhxEvent.getId());
		Date oldStartTime = graphic.getStartTime();
		Date oldEndTime = graphic.getEndTime();
		Boolean oldAvailability = graphic.getAvailable();
		graphic.setAvailable(allowReservation);
		graphic.setStartTime(dhxEvent.getStart_date());
		graphic.setEndTime(dhxEvent.getEnd_date());
		graphic.setTitle(dhxEvent.getText());
		graphic = services.getGraphicService().save(graphic);
		//notify users
		services.getUserNotificationsService().graphicChangedByAnimator(graphic);
		EventState inBasketState = services.getStateDAO().findOne(
				EventState.IN_A_BASKET);
		EventState readyToAcceptState = services.getStateDAO().findOne(
				EventState.READY_TO_ACCEPT);
		UserEventRole guestRole = services.getUserEventRoleService().findOne(
				UserEventRole.GUEST);
		UserDecision notInvited = services.getUserEventDecisionDAO().findOne(
				UserDecision.NOT_INVITED);
		UserDecision invited = services.getUserEventDecisionDAO().findOne(
				UserDecision.INVITED);
		graphic.getEvents()
				.stream()
				.map(e -> e.getUsersEvent())
				.flatMap(ue -> ue.stream())
				.filter(ue -> ue.getRole().equals(guestRole)
						&& !ue.getDecision().equals(notInvited))
				.forEach(ue -> {
					ue.setDecision(invited);
					services.getUserEventService().save(ue);
				});

		//TODO jeśli został zmieniony czas lub już nie jest dostępny
		//jeśli zmieniony czas to cofasz do stanu przed , ale nie wywalasz do kosza,
		//jeśli niedostępny to do kosza
		if (!graphic.getAvailable().equals(oldAvailability)
				|| graphic.getStartTime().compareTo(oldStartTime) != 0
				|| graphic.getEndTime().compareTo(oldEndTime) != 0) {
			graphic.getEvents()
					.stream()
					.forEach(
							e -> {
								if (e.getState().equals(readyToAcceptState)) {
									services.getEventToApproveService()
											.removeEventFromWaitingForCheckByManager(
													e);
								}
								e.setState(inBasketState);
								e.setGraphic(null);
								services.getEventService().save(e);
							});
		}
	}
}
