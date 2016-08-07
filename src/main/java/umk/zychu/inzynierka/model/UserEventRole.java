package umk.zychu.inzynierka.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@SuppressWarnings("serial")
@Entity
@Table(name="user_event_role")
public class UserEventRole extends BaseEntity {
	
	@Column(name="event_role")
	String eventRole;
	
	@OneToMany(mappedBy = "role")
	List<UserEvent> usersEvent;
	
	public void setEventRole(String role){
		this.eventRole = role;
	}
	
	public String getEventRole(){
		return this.eventRole;
	}
		
	public void setUserEvent(List<UserEvent> events){
		this.usersEvent = events;
	}
	
	public List<UserEvent> getUsersEvent(){
		return this.usersEvent;
	}
	
	public static final Integer ORGANIZER = 1, GUEST = 2;	
}
