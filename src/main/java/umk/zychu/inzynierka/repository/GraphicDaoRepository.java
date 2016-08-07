package umk.zychu.inzynierka.repository;

import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.model.Graphic;

@Transactional
public interface GraphicDaoRepository extends BaseRepository<Graphic, Integer >{
}
