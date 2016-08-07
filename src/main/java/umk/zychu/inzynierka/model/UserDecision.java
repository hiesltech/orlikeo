package umk.zychu.inzynierka.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "user_decision")
public class UserDecision extends BaseEntity {

	@Column(name = "decision")
	String userDecision;

	@OneToMany( mappedBy = "decision")
	List<UserEvent> usersEvent;
	
	UserDecision(){
		super();
	}
	
	public UserDecision(String userDecision) {
		super();
		this.userDecision = userDecision;
	}

	public void setUserDecision(String decision) {
		this.userDecision = decision;
	}

	public String getUserDecision() {
		return this.userDecision;
	}

	public void setUsersEvent(List<UserEvent> events) {
		this.usersEvent = events;
	}

	public List<UserEvent> getUsersEvent() {
		return this.usersEvent;
	}

	public static final Integer INVITED = 1, ACCEPTED = 2, REJECTED = 3,
			NOT_INVITED = 4;
}
