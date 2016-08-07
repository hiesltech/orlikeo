package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.model.Graphic;

import java.util.List;

public interface GraphicService {
	Graphic findOne(Integer id);
	List<Graphic> findAll();
	Graphic save(Graphic graphic);
	void delete(Graphic entity);
}
