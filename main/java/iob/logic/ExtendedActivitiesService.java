package iob.logic;

import java.util.List;

import iob.logic.boundaries.ActivityBoundary;

public interface ExtendedActivitiesService extends ActivitiesService {
	
	public List<ActivityBoundary> getAllActivities(String userDomain,String userEmail , int size , int page);
	
	public void deleteAllActivites (String userDomain , String userEmail);
	

}
