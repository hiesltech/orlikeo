package umk.zychu.inzynierka.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.dhtmlx.planner.DHXEv;


@SuppressWarnings("serial")
@Entity
@Table(name="orlik_graphic")
public class Graphic implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	
	@Column(name="title")
	String title;
	
	@Column(name="start_time")
	Date startTime;
	
	@Column(name="end_time")
	Date endTime;
	
	@Column(name="available")
	Boolean available;
	
	@ManyToOne
	@JoinColumn(name="orlik_id", referencedColumnName="id")
	Orlik orlik;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="graphic")
	List<Event> events;
	
	public Graphic(){
	}
	
	public Graphic(DHXEv event, Orlik orlik) {
		super();
		this.title = event.getText();
		this.startTime = event.getStart_date();
		this.endTime = event.getEnd_date();
		this.available = false;
		this.orlik = orlik;
	}
	
	public void setTitle(String name){
		title = name;
	}
	
	public String getTitle(){
		return title;
	}
	
	public void setStartTime(Date date){
		startTime = date;
	}
	
	public Date getStartTime(){
		return startTime;
	}
	
	public void setEndTime(Date date){
		endTime = date;
	}
	
	public Date getEndTime(){
		return endTime;
	} 
	
	public void setAvailable(Boolean bool){
		available = bool;
	}
	
	public Boolean getAvailable(){
		return available;
	}
	
	public void setOrlik(Orlik orlik){
		this.orlik = orlik;
	}
	
	public Orlik getOrlik(){
		return orlik;
	}
	
	public void setEvent(List<Event> events){
		this.events = events;
	}
	
	public List<Event> getEvents(){
		return this.events;
	}
	
	public Integer getId() {
		return id;
	}

	public boolean isNew() {
		return id == null;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((available == null) ? 0 : available.hashCode());
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orlik == null) ? 0 : orlik.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Graphic)) {
			return false;
		}
		Graphic other = (Graphic) obj;
		if (available == null) {
			if (other.available != null) {
				return false;
			}
		} else if (!available.equals(other.available)) {
			return false;
		}
		if (endTime == null) {
			if (other.endTime != null) {
				return false;
			}
		} else if (!endTime.equals(other.endTime)) {
			return false;
		}
		if (events == null) {
			if (other.events != null) {
				return false;
			}
		} else if (!events.equals(other.events)) {
			return false;
		}
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (orlik == null) {
			if (other.orlik != null) {
				return false;
			}
		} else if (!orlik.equals(other.orlik)) {
			return false;
		}
		if (startTime == null) {
			if (other.startTime != null) {
				return false;
			}
		} else if (!startTime.equals(other.startTime)) {
			return false;
		}
		if (title == null) {
			if (other.title != null) {
				return false;
			}
		} else if (!title.equals(other.title)) {
			return false;
		}
		return true;
	}	
}
