package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.Graphic;

import com.dhtmlx.planner.DHXEvent;

public class DHXCustomEvent extends DHXEvent{

	public Boolean allow;
	public long orlik;
	
	public DHXCustomEvent() {
		super();
		this.allow = false;
		this.orlik = 0;
	}

	public DHXCustomEvent(Graphic graphic) {
		super(graphic.getId(), graphic.getStartTime(), graphic.getEndTime(), graphic.getTitle());
		this.allow = graphic.getAvailable();
		this.orlik = graphic.getOrlik().getId();
	}
	
	public Boolean getAllow() {
		return allow;
	}

	public void setAllow(Boolean allow) {
		this.allow = allow;
	}
	
	
	public long getOrlik() {
		return orlik;
	}
	
	public void setOrlik(long orlik) {
		this.orlik = orlik;
	}	
}
