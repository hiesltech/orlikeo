package umk.zychu.inzynierka.repository;

import org.springframework.transaction.annotation.Transactional;
import umk.zychu.inzynierka.model.EventState;

@Transactional
public interface EventStateDaoRepository extends BaseRepository<EventState, Integer>{
}
