package umk.zychu.inzynierka.repository;

import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.model.UserEvent;

@Transactional
public interface UserEventDaoRepository extends BaseRepository<UserEvent, Integer>{	
}
