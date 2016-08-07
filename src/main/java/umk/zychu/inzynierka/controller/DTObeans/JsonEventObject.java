package umk.zychu.inzynierka.controller.DTObeans;

import umk.zychu.inzynierka.model.Graphic;

public class JsonEventObject {
	
	public JsonEventObject(Graphic graphic) {
		id = graphic.getId();
		title = graphic.getTitle();
		start = (graphic.getStartTime()).getTime();
		end = (graphic.getEndTime()).getTime();
		url = null;
		available = graphic.getAvailable();
		allDay = false;
	}
	
	public long id;
	public String title;
	public long start;
	public long end;
	public Boolean available;
	public String url;
	public Boolean allDay;
}
