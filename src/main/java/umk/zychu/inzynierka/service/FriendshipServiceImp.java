package umk.zychu.inzynierka.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.User;
import umk.zychu.inzynierka.repository.FriendshipDaoRepository;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class FriendshipServiceImp implements FriendshipService{

	@Autowired
	FriendshipDaoRepository friendshipDAO;
	@Autowired
	UserService userService;
	@Autowired
	UserEventService userEventService;
	@Autowired
	UserNotificationsService userNotificationsService;

	@Override
	public List<User> getFriendsByState(Integer state){
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		List<User> friends = new LinkedList<User>();
		user.getFriendships().stream()
			.filter(f -> f.getState().equals(state))
			.forEach(f -> { 
					if(f.getFriendAccepter().equals(user))
						friends.add(f.getFriendRequester());
					else
						friends.add(f.getFriendAccepter());	
					});
		return friends;
	}
	
	@Override
	public Boolean checkIfTheyHadContacted(User user, User user2) {
		long counted = user.getFriendships().stream()
				.filter(f -> (f.getFriendAccepter().equals(user2) || f.getFriendRequester().equals(user2)))
				.count();
		if(counted > 0){
			return true;
		}
		else{
			return false;
		}	
	}

	@Override
	public List<User> getPendedFriendshipRequests() {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		  List<User> friends = user.getFriendships().stream()
				  .filter(f -> f.getActionUser().equals(user) && f.getState() == Friendship.INVITED)
				  .map(f-> {
					  if(f.getFriendAccepter().equals(user))
						  return f.getFriendRequester();
					  else
						  return f.getFriendAccepter();
				  })
				  .collect(Collectors.toList());
		  return friends;
	}

	@Override
	public List<User> getReceivedFriendshipRequests(){
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		List<User> friends = user.getFriendships().stream()
					.filter(f -> !f.getActionUser().equals(user) && f.getState() == Friendship.INVITED)
					.map(f-> {
					  if(f.getFriendAccepter().equals(user))
						  return f.getFriendRequester();
					  else
						  return f.getFriendAccepter();
				  })
				  .collect(Collectors.toList());
		return friends;
	}
	
	@Override
	public void inviteUserToFriends(User user, User invitedUser) {
		try{
			Friendship friendship = null;
			if(checkIfTheyHadContacted(user, invitedUser)){
				Optional<Friendship> friendshipOpt = user.getFriendships().stream()
						.filter(f -> f.getFriendAccepter().equals(invitedUser) 
								|| f.getFriendRequester().equals(invitedUser))
						.findFirst();
				if(friendshipOpt.isPresent()){
					friendship = friendshipOpt.get();
					friendship = changeFriendshipStatus(friendship, Friendship.INVITED);
				}
			}else{
				friendship = new Friendship(user, invitedUser);
				friendship = friendshipDAO.save(friendship);
			}
			if(friendship != null)
				userNotificationsService.invitation(friendship);
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	
	@Override
	public void acceptUserInvitation(String email) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		User userToAccept = userService.getUser(email);
		Optional<Friendship> friendship = user.getFriendships().stream()
			.filter(f -> (f.getFriendRequester().equals(user) || f.getFriendAccepter().equals(user)) 
					&& f.getActionUser().equals(userToAccept)).findFirst();
		if(friendship.isPresent())
		{
			Friendship f = friendship.get();
			f = changeFriendshipStatus(f, Friendship.ACCEPTED);
			userNotificationsService.acceptation(f);
		}
	}

	@Override
	public Friendship getUsersFriendship(User user, User userRequest) {
		Optional<Friendship> friendshipOpt = user.getFriendships().stream()
				.filter(f -> (f.getFriendRequester().equals(userRequest) 
						|| f.getFriendAccepter().equals(userRequest)))
				.findFirst();
		if(friendshipOpt.isPresent())
		{
			return friendshipOpt.get();
		}else{
			return null;
		}
	}

	@Override
	public void cancelFriendInvitation(String email) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		User userForCancel = userService.getUser(email);
		Optional<Friendship> friendshipOpt =  user.getFriendships().stream()
						.filter(f ->
								(f.getFriendAccepter().equals(userForCancel)
										|| f.getFriendRequester().equals(userForCancel))
										&& f.getState() == Friendship.INVITED)
						.findAny();
		if(friendshipOpt.isPresent()){
			Friendship f = friendshipOpt.get();
			userNotificationsService.cancelInvitation(user, userForCancel);
			friendshipDAO.delete(f);
		}	
	}

	// to do
	@Override
	@Transactional
	public void blockUser(String email) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		User userForBlock = userService.getUser(email);
		long count = user.getFriendships().stream()
							.filter(f -> (f.getFriendAccepter().equals(user) && f.getFriendRequester().equals(userForBlock)) 
									|| (f.getFriendAccepter().equals(userForBlock) && f.getFriendRequester().equals(user)))
							.count();
		if(count>0){
			user.getOrganizedEventsList()
					.forEach(event -> { 
						event.getUsersEvent().stream()
							.filter(userEvent -> userEvent.getUser().equals(userForBlock))
						.forEach(y -> {
							userEventService.delete(y);
					});
			});
			userForBlock.getOrganizedEventsList()
					.forEach(event -> { 
						event.getUsersEvent().stream()
								.filter(userEvent -> userEvent.getUser().equals(user))
					.forEach(y -> {
						userEventService.delete(y);
					});
			});	
			Optional<Friendship> friendshipOpt = user.getFriendships().stream()
					.filter(f -> f.getFriendAccepter().equals(userForBlock) 
							|| f.getFriendRequester().equals(userForBlock))
					.findFirst();
			if(friendshipOpt.isPresent()){
				Friendship friendship = friendshipOpt.get();
				friendship = changeFriendshipStatus(friendship, Friendship.BLOCKED);
				userNotificationsService.blocking(friendship);
			}
		}else{
			Friendship friendship = new Friendship(user, userForBlock);
			friendship = friendshipDAO.save(friendship);
			friendship.setState(Friendship.BLOCKED);
			friendship = friendshipDAO.save(friendship);
			userNotificationsService.blocking(friendship);
		}
	}

	@Override
	public void rejectUserFriendRequest(String email) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		User userRequest = userService.getUser(email);
		Optional<Friendship> friendshipOpt = user.getFriendships().stream()
			.filter(f -> f.getActionUser().equals(userRequest) && f.getState() == Friendship.INVITED).findFirst();
		if(friendshipOpt.isPresent()){
			Friendship friendship = friendshipOpt.get();
			friendship = changeFriendshipStatus(friendship, Friendship.DECLINED);
			userNotificationsService.denial(friendship);
		}
	}

	@Override
	public List<User> getBlockedUsers() {		
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		List<User> blockedUsers = user.getFriendships().stream()
									.filter(f -> f.getActionUser().equals(user) && f.getState() == Friendship.BLOCKED)
									.map(f -> {
										if(f.getFriendAccepter().equals(user))
											return f.getFriendRequester();
										else
											return f.getFriendAccepter();
									}).collect(Collectors.toList());
		return blockedUsers;
	}

	private Friendship changeFriendshipStatus(Friendship friendship, int status) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		friendship.setState(status);
		friendship.setActionUser(user);
		return friendshipDAO.save(friendship);
	}
	
	@Override
	public void removeFriendship(String email) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		User userForRemove = userService.getUser(email);
		Optional<Friendship> friendshipOpt = user.getFriendships().stream()
							.filter(f -> f.getFriendAccepter().equals(userForRemove) 
										|| f.getFriendRequester().equals(userForRemove)).findFirst();
		if(friendshipOpt.isPresent()) {
			userNotificationsService.remove(user, userForRemove);
			friendshipDAO.delete(friendshipOpt.get());
		}
	}

	@Override
	public void unblockUser(String email) {
		User user = userService.getUser(SecurityContextHolder.getContext().getAuthentication().getName());
		User userForUnblock = userService.getUser(email);
		user.getFriendships().stream()
							.filter(f -> f.getFriendAccepter().equals(userForUnblock)
									|| f.getFriendRequester().equals(userForUnblock))
							.findFirst()
							.ifPresent(f -> {
								userNotificationsService.unblocking(f);
								friendshipDAO.delete(f);
							});
	}
}
