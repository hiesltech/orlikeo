package umk.zychu.inzynierka.controller.DTObeans;

import java.util.Date;

public class UserGameDetails {



	UserGameDetails() {
		super();
	}

	public UserGameDetails(Integer eventId, Integer stateId, Integer orlikId, String address,
			String city, String organizerEmail, Date startDate, Date endDate,
			Integer decisionId, Integer roleId, Boolean permission,
			int playersLimit, long willCome, long invited, Boolean lights,
			Boolean water, Boolean shower, String shoes) {
		super();
		this.eventId = eventId;
		this.stateId = stateId;
		this.orlikId = orlikId;
		this.address = address;
		this.city = city;
		this.organizerEmail = organizerEmail;
		this.startDate = startDate;
		this.endDate = endDate;
		this.decisionId = decisionId;
		this.roleId = roleId;
		this.permission = permission;
		this.willCome = willCome;
		this.playersLimit = playersLimit;
		this.invited = invited;
		this.lights = lights;
		this.water = water;
		this.shower = shower;
		this.shoes = shoes;
	}

	Integer eventId;
	Integer stateId;
	Integer orlikId;
	String address;
	String city;
	String organizerEmail;
	Date startDate;
	Date endDate;
	Integer decisionId;
	Integer roleId;
	Boolean permission;
	long willCome;
	Integer playersLimit;
	long invited;
	Boolean lights;
	Boolean water;
	Boolean shower;
	
	public Integer getOrlikId() {
		return orlikId;
	}

	public void setOrlikId(Integer orlikId) {
		this.orlikId = orlikId;
	}

	public Boolean getLights() {
		return lights;
	}

	public void setLights(Boolean lights) {
		this.lights = lights;
	}

	public Boolean getWater() {
		return water;
	}

	public void setWater(Boolean water) {
		this.water = water;
	}

	public Boolean getShower() {
		return shower;
	}

	public void setShower(Boolean shower) {
		this.shower = shower;
	}

	public String getShoes() {
		return shoes;
	}

	public void setShoes(String shoes) {
		this.shoes = shoes;
	}

	String shoes;

	public long getInvited() {
		return invited;
	}

	public void setInvited(long invited) {
		this.invited = invited;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Boolean getPermission() {
		return permission;
	}

	public void setPermission(Boolean permission) {
		this.permission = permission;
	}

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public Integer getStateId() {
		return stateId;
	}

	public void setStateId(Integer stateId) {
		this.stateId = stateId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getOrganizerEmail() {
		return organizerEmail;
	}

	public void setOrganizerEmail(String organizerEmail) {
		this.organizerEmail = organizerEmail;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getDecisionId() {
		return decisionId;
	}

	public void setDecisionId(Integer decisionId) {
		this.decisionId = decisionId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public long getWillCome() {
		return willCome;
	}

	public void setWillCome(long willCome) {
		this.willCome = willCome;
	}

	public Integer getPlayersLimit() {
		return playersLimit;
	}

	public void setPlayersLimit(Integer playersLimit) {
		this.playersLimit = playersLimit;
	}
	
	@Override
	public String toString() {
		return "UserGameDetails [eventId=" + eventId + ", stateId=" + stateId
				+ ", orlikId=" + orlikId + ", address=" + address + ", city="
				+ city + ", organizerEmail=" + organizerEmail + ", startDate="
				+ startDate + ", endDate=" + endDate + ", willCome=" + willCome
				+ ", playersLimit=" + playersLimit + ", water=" + water
				+ ", shower=" + shower + ", shoes=" + shoes + "]";
	}
}
