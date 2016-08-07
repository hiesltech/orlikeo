package umk.zychu.inzynierka.model;

import javax.persistence.*;
import java.io.Serializable;


@SuppressWarnings("serial")
@Entity
@Table(name = "user_event")
public class UserEvent extends BaseEntity implements Serializable {
	
	public UserEvent() {
		super();
	}

	public UserEvent (User userTarget, UserEventRole eventRole, 
			UserDecision userDecision, Boolean permission, Event event, User userInviter){
		this.user = userTarget;
		this.role = eventRole;
		this.decision = userDecision;
		this.userPermission = permission;
		this.event = event;
		this.inviter = userInviter;
	}
	
	@Column(name = "user_permission")
	boolean userPermission;
	
	@ManyToOne()
	@JoinColumn(name = "inviter_id", referencedColumnName = "id")
	User inviter;
	
	
	@ManyToOne
	@JoinColumn(name = "user_role", referencedColumnName = "id")
	UserEventRole role;
	
	@ManyToOne
	@JoinColumn(name = "user_decision", referencedColumnName = "id")
	UserDecision decision;
	
	@ManyToOne
	@JoinColumn(name = "event_id", referencedColumnName = "id", insertable = true, updatable = true)
	Event event;
	
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	User user;
	
	public User getInviter() {
		return inviter;
	}

	public void setInviter(User inviter) {
		this.inviter = inviter;
	}
	
	public void setEvent(Event event){
		this.event = event;
	}
	
	public Event getEvent(){
		return this.event;
	}
	
	public void setUser(User user){
		this.user = user;
	}
	
	public User getUser(){
		return this.user;
	}
	
	public void setUserPermission(boolean permission){
		this.userPermission = permission;
	}
	
	public boolean getUserPermission(){
		return this.userPermission;
	}
	
	public void setRole(UserEventRole role){
		this.role = role;
	}
	
	public UserEventRole getRole(){
		return this.role;
	}
	
	public void setDecision(UserDecision decision){
		this.decision = decision;
	}
	
	public UserDecision getDecision(){
		return this.decision;
	}
}
