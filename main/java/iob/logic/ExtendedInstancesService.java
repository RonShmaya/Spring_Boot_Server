package iob.logic;

import java.util.List;

import iob.logic.boundaries.InstanceBoundary;

public interface ExtendedInstancesService extends InstancesService {

	
	
	public List<InstanceBoundary> getInstancesByName(String name, int size, int page);

	public List<InstanceBoundary> getInstancesByType(String type, int size, int page);

	public List<InstanceBoundary> getInstancesByNear(double lat, double lng, double distance, int size, int page);
	
	public List<InstanceBoundary> getAllInstances(int size, int page);
	
	public InstanceBoundary getSpecificInstanceMustBeActive(String instanceDomain, String instanceId);
	
	public List<InstanceBoundary> getAllInstancesMustBeActive(int size, int page); 
	
	public List<InstanceBoundary> getInstancesByNameMustBeActive(String name, int size, int page);
	
	public List<InstanceBoundary> getInstancesByTypeMustBeActive(String type, int size, int page);

	public List<InstanceBoundary> getInstancesByNearMustBeActive(double lat, double lng, double distance, int size, int page);
	
	public void deleteAllInstances (String userDomain, String userEmail);
	
	public InstanceBoundary updateInstance (String userDomain, String userEmail , String instanceDomain, String instanceId, InstanceBoundary update);
	
	public InstanceBoundary getInstanceHandler(String userDomain, String userEmail , String instanceDomain, String instanceId);
	
	public List<InstanceBoundary> getAllInstancesHandler(String userDomain, String userEmail , int size, int page);
	
	public List<InstanceBoundary> getInstancesByNameHandler(String userDomain, String userEmail , String name, int size, int page);

	public List<InstanceBoundary> getInstancesByTypeHandler(String userDomain, String userEmail , String type, int size, int page);

	public List<InstanceBoundary> getInstancesByLocationHandler(String userDomain, String userEmail , double lat, double lng, double distance, int size, int page);
	
}
