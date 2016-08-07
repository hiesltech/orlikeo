package umk.zychu.inzynierka.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@SuppressWarnings("serial")
@Entity
@Table(name="events_to_approve")
public class EventToApprove extends BaseEntity{
	
	@OneToOne
	@JoinColumn(name="event_id", unique = true)
	private Event event;
	
	@Column(name="checked")
	private Boolean checked = false;
	
	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public EventToApprove() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public EventToApprove(Event event, Boolean checked){
		this.event = event;
		this.checked = checked;
	}

	public EventToApprove(Event event) {
		// TODO Auto-generated constructor stub
		this.event = event;
	}
	
	public Boolean isChecked(){
		return checked;
	}
}
