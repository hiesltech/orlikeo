package umk.zychu.inzynierka.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@SuppressWarnings("serial")
@Entity
@Table(name="event")
public class Event extends BaseEntity {

	@Column(name = "players_limit")
	Integer playersLimit;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
	List<UserEvent> usersEvent;
	
	@Column(name="creation_date")
	Date creationDate;

	@ManyToOne
	@JoinColumn(name = "graphic_id", referencedColumnName = "id", nullable = true)
	Graphic graphic;
	
	@ManyToOne
	@JoinColumn(name = "state_id", referencedColumnName = "id")
	EventState state;
	
	@ManyToOne
	@JoinColumn(name="user_organizer", referencedColumnName="id")
	User userOrganizer;

	public Integer getPlayersLimit() {
		return playersLimit;
	}
	
	public void setPlayersLimit(Integer playersLimit) {
		this.playersLimit = playersLimit;
	}

	public void setUsersEvent(List<UserEvent> usersEvent){
		this.usersEvent = usersEvent;
	}
	
	public List<UserEvent> getUsersEvent(){
		return this.usersEvent;
	}
	
	public void setCreationDate(Date date){
		creationDate = date;
	}
	
	public Date getCreationDate(){
		return creationDate;
	}
	
	public void setGraphic(Graphic graphic){
		this.graphic = graphic;
	}
	
	public Graphic getGraphic(){
		return this.graphic;
	}
	
	public void setState(EventState state){
		this.state = state;
	}
	
	public EventState getState(){
		return state;
	}
	
	public User getUserOrganizer() {
		return userOrganizer;
	}
	
	public void setUserOrganizer(User userOrganizer) {
		this.userOrganizer = userOrganizer;
	}
	
	public Event(){
		super();
	}
	
	public Event(User organizer, Graphic graphic, EventState state, Integer playersLimit){
		super();
		this.userOrganizer = organizer;
		this.graphic = graphic;
		this.state = state;
		this.playersLimit = playersLimit;
		if(this.isNew()) this.creationDate = new Date();
	}	
}
