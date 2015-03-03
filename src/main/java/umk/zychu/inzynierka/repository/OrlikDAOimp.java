package umk.zychu.inzynierka.repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import umk.zychu.inzynierka.model.Orlik;

@Repository
public class OrlikDAOimp implements OrlikDAO{
	
	@PersistenceContext
	private EntityManager em;
		
	@Override
	@SuppressWarnings("unchecked")
	public Orlik getOrlikById(long id){
		List<Orlik> orlikList = new ArrayList<Orlik>();
		Query query = em.createQuery("from Orlik o where o.id = :id");
		query.setParameter("id", id);
		orlikList = query.getResultList();
		if(orlikList.size() > 0){
			return orlikList.get(0);
		}
		else{
			return null;
		}
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Orlik> getOrliks(){
		List<Orlik> orlikList = new ArrayList<Orlik>();
		Query query = em.createQuery("from Orlik");
		orlikList = query.getResultList();
		if(orlikList.size() > 0){
			return orlikList;
		}
		else{
			return null;
		}
	}
	
	public void saveOrlik(Orlik orlik){
		if (orlik.isNew()) {
			em.persist(orlik);
		} else {
			em.merge(orlik);
		}
		
	}
	
	
	@Override
	@SuppressWarnings("unchecked")
	public Map<String, String> getOrliksIdsAndNames(){

		List<Orlik> orlikList = new ArrayList<Orlik>();
		Query query = em.createQuery("from Orlik");
		orlikList = query.getResultList();
		if(orlikList.size() > 0){
			Map<String, String> map = new LinkedHashMap<String, String>();
			map.put("0", "Wybierz orlik...");
			for(Iterator<Orlik> iter = orlikList.iterator(); iter.hasNext(); )
			{
				Orlik item = iter.next();
				map.put(item.getId().toString(), item.getAddress());		
			}
			return map;
		}
		else{
			return null;
		}
		
	}
	

	
	
}
