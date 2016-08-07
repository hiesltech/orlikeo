package umk.zychu.inzynierka.service;

import umk.zychu.inzynierka.controller.DTObeans.OrlikForm;
import umk.zychu.inzynierka.controller.DTObeans.UserGameDetails;
import umk.zychu.inzynierka.model.Orlik;
import umk.zychu.inzynierka.model.User;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface OrlikService {
	Orlik getOrlikById(Integer id);
	Orlik saveOrlik(Orlik orlik);
	Map<String, String> getOrliksIdsAndNames();
	/*List<User> getOrlikManagersByOrlik(Orlik orlik);*/
	List<UserGameDetails> getAllByManager(String username);
	List<Orlik> findAll();
	void delete(Orlik orlik);
	Optional<Orlik> getAnimatorOrlik(User user);
	Boolean isOrlikManager(User user);
	OrlikForm saveOrUpdateOrlik(OrlikForm form);
}
