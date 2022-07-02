package iob.logic;

import java.util.List;

import iob.logic.boundaries.UserBoundary;

public interface UsersService {
	
	public UserBoundary createUser(UserBoundary userBoundary);
	public UserBoundary login(String userDomain, String userEmail);
	public UserBoundary updateUser(String userDomain ,String userEmail , UserBoundary update);
	
	@Deprecated
	public void deleteAllUsers();
	@Deprecated
	public List<UserBoundary> getAllUsers();

	

}
