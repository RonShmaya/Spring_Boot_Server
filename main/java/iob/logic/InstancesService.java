package iob.logic;

import java.util.List;

import iob.logic.boundaries.InstanceBoundary;

public interface InstancesService {



	public InstanceBoundary getSpecificInstance (String instanceDomain, String instanceId);
	
	@Deprecated
	public InstanceBoundary createInstance (InstanceBoundary instanceBoundary);
	
	@Deprecated
	public InstanceBoundary updateInstance (String instanceDomain, String instanceId, InstanceBoundary update);
	
	@Deprecated
	public void deleteAllInstances ();
	
	@Deprecated
	public List<InstanceBoundary> getAllInstances();
}
