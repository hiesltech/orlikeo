package umk.zychu.inzynierka.controller.util;

public enum EventType {
	ORGANIZED(1),
	INVITATIONS(2);
	
	private final Integer value;
	
	EventType(Integer value){
		this.value = value;
	}
	
	public Integer getValue(){
		return value;
	}
}
