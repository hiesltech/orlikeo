package umk.zychu.inzynierka.controller.DTObeans;

public class ChoosenOrlikBean {

	Integer Id;
	Integer eventId;

	public Integer getEventId() {
		return eventId;
	}

	public void setEventId(Integer eventId) {
		this.eventId = eventId;
	}

	public ChoosenOrlikBean() {
		Id = -1;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public Integer getId() {
		return Id;
	}	
}
