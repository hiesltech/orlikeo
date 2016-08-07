package umk.zychu.inzynierka.controller.DTObeans;

import java.util.List;

public class RegisterEventForm {
	
	Integer eventId;
	Integer graphicId;
	Integer usersLimit;
	List<RegisterEventUser> eventFormMembers;
	
	public RegisterEventForm(){}
	
	public RegisterEventForm(Integer id, List<RegisterEventUser> users) {
		eventId = id;
		eventFormMembers = users;
	}

	public Integer getEventId() {
		return eventId;
	}
	
	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}
	
	public List<RegisterEventUser> getEventFormMembers() {
		return eventFormMembers;
	}
	
	public void setEventFormMembers(List<RegisterEventUser> eventFormMembers) {
		this.eventFormMembers = eventFormMembers;
	}
	
	public void setGraphicId(Integer id){
		this.graphicId = id;
	}
	
	public Integer getGraphicId(){
		return graphicId;
	}
	
	public void setUsersLimit(Integer limit){
		this.usersLimit = limit;
	}
	
	public Integer getUsersLimit(){
		return usersLimit;
	}
}
