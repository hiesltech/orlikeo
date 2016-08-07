package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import umk.zychu.inzynierka.model.UserEventRole;
import umk.zychu.inzynierka.repository.UserEventRoleDAOrepository;


@Service
@Transactional
public class UserEventRoleServiceImp implements UserEventRoleService{

	@Autowired
	private UserEventRoleDAOrepository roleDAO;
	
	@Override
	public UserEventRole findOne(Integer id) {
		return roleDAO.findOne(id);
	}
}
