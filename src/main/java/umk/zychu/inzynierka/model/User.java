package umk.zychu.inzynierka.model;


import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
@Entity
@Table(name="user")
public class User extends BaseEntity
{
	@Email
	@NotBlank
	@Size(min = 6)
	@Column(name = "email")
	String email;
	
    @NotBlank
    @Size(min = 6)
    @Column(name = "password")
    String password;
	
	@Column(name = "name")
	String name;
	
	@Column(name = "surname")
	String surname;
	
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	@Temporal(TemporalType.DATE)
	@Column(name = "date_of_birth")
	Date dateOfBirth;
	
	@Column(name = "position")
	String position;
	
	@Column(name = "weight")
	Integer weight;
	
	@Column(name = "height")
	Integer  height;
	
	@Column(name = "foot")
	String foot;

	public void setFriendRequesterList(List<Friendship> friendRequesterList) {
		this.friendRequesterList = friendRequesterList;
	}

	public void setFriendAccepterList(List<Friendship> friendAccepterList) {
		this.friendAccepterList = friendAccepterList;
	}

	@OneToMany(mappedBy="friendRequester", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Friendship> friendRequesterList;

	@OneToMany(mappedBy="friendAccepter", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Friendship> friendAccepterList;

	@OneToMany(mappedBy = "actionUser", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Friendship> actionUsersList;
	
	/*@ManyToMany(mappedBy = "orlikManagers")
    private List<Orlik> isOrliksManager;*/



	@OneToMany(mappedBy = "inviter", fetch = FetchType.EAGER)
	private List<UserEvent> usersEventsFriendsInvited;

	@OneToMany(mappedBy="userOrganizer", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Event> organizedEventsList;

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "user", orphanRemoval = true)
	private List<UserEvent> userEvents;

	public List<UserNotification> getUserNotifications() {
		return userNotifications;
	}

	public void setUserNotifications(List<UserNotification> userNotifications) {
		this.userNotifications = userNotifications;
	}

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER, orphanRemoval = true)
	private List<UserNotification> userNotifications;

	
	public List<Friendship> getFriendships(){
		List<Friendship> friendships = new ArrayList<Friendship>();
		friendships.addAll(getFriendAccepterList());
		friendships.addAll(getFriendRequesterList());
		return friendships;
	}

	public List<User> getUserFriends(){
		User user = this;
		return getFriendships().stream()
				.map(f -> {
					if(f.getFriendAccepter().equals(user)) {
						return f.getFriendRequester();
					}
					else {
						return f.getFriendAccepter();
					}
				}).collect(Collectors.toList());
	}
	
	public List<Event> getOrganizedEventsList() {
		return organizedEventsList;
	}
	public void setOrganizedEventsList(List<Event> organizedEventsList) {
		this.organizedEventsList = organizedEventsList;
	}

/*	public List<Orlik> getIsOrliksManager() {
		return isOrliksManager;
	}

	public void setIsOrliksManager(List<Orlik> isOrliksManager) {
		this.isOrliksManager = isOrliksManager;
	}*/

	//getter and setter methods
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	
	public void setDateOfBirth(Date dateOfBirth){
		this.dateOfBirth = dateOfBirth;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position){
		this.position = position;
	}
	
	public Integer getWeight() {
		return weight;
	}
	
	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Integer getHeight() {
		return height;
	}
	
	public void setHeight(Integer height) {
		this.height = height;
	}
	
	public String getFoot() {
		return foot;
	}
	
	public void setFoot(String foot) {
		this.foot = foot;
	}
	
	public void setUserEvents(List<UserEvent> userEvents){
		this.userEvents = userEvents;
	}
	
	public List<UserEvent> getUserEvents(){
		return this.userEvents;
	}
	
	public List<Friendship> getActionUsersList() {
		return actionUsersList;
	}
	public void setActionUsersList(List<Friendship> actionUsersList) {
		this.actionUsersList = actionUsersList;
	}
	public List<UserEvent> getUsersEventsFriendsInvited() {
		return usersEventsFriendsInvited;
	}
	public void setUsersEventsFriendsInvited(
			List<UserEvent> usersEventsFriendsInvited) {
		this.usersEventsFriendsInvited = usersEventsFriendsInvited;
	}
	public List<Friendship> getFriendRequesterList() {
		return friendRequesterList;
	}
	public List<Friendship> getFriendAccepterList() {
		return friendAccepterList;
	}
	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [email=" + email + ", userEvents=" + userEvents
				+ "]";
	}
}
