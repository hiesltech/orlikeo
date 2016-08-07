package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.model.UserDecision;
import umk.zychu.inzynierka.repository.UserEventDecisionDAOrepository;

@Service
@Transactional
public class UserEventDecisionImp implements UserEventDecisionService{

	@Autowired
	UserEventDecisionDAOrepository decisionDAO;
	
	@Override
	public UserDecision findOne(Integer id) {
		return decisionDAO.findOne(id);
	}
}
