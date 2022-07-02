package iob.logic.boundaries;


public class Instance {
	private InstanceId instanceId;

	public Instance() {
		
	}



	public Instance(InstanceId instanceId) {
		this.instanceId = instanceId;
	}



	public InstanceId getInstanceId() {
		return instanceId;
	}


	public void setInstanceId(InstanceId instanceId) {
		this.instanceId = instanceId;
	}
	
	
	
}
