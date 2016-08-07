package umk.zychu.inzynierka.repository;

import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.model.Event;

@Transactional
public interface EventDaoRepository extends BaseRepository<Event, Integer>{
}
