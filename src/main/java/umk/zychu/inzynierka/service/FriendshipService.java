package umk.zychu.inzynierka.service;

import java.util.List;

import umk.zychu.inzynierka.model.Friendship;
import umk.zychu.inzynierka.model.User;

public interface FriendshipService {
	Boolean checkIfTheyHadContacted(User userRequest1, User userRequest2);	
	void inviteUserToFriends(User user, User invitedUser);
	void acceptUserInvitation(String email);
	Friendship getUsersFriendship(User user, User userRequest);
	void cancelFriendInvitation(String email);
	void blockUser(String email);
	void rejectUserFriendRequest(String email);
	List<User> getPendedFriendshipRequests();
	List<User> getFriendsByState(Integer state);
	List<User> getReceivedFriendshipRequests();
	List<User> getBlockedUsers();
	void removeFriendship(String email);
	void unblockUser(String email);
}
