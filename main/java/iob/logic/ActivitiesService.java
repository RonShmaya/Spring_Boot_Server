package iob.logic;

import java.util.List;

import iob.logic.boundaries.ActivityBoundary;

public interface ActivitiesService {

	@Deprecated
	public List<ActivityBoundary> getAllActivities();
	@Deprecated
	public void deleteAllActivites ();

	
	public Object invokeActivity (ActivityBoundary activity);

}
