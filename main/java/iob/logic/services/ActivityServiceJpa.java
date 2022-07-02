package iob.logic.services;


import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import iob.data.ActivityRepository;
import iob.data.InstanceEntity;
import iob.data.InstanceRepository;
import iob.data.UserEntity;
import iob.data.UserRepository;
import iob.data.UserRoleEntity;
import iob.data.ActivityEntity;

import iob.logic.ExtendedActivitiesService;
import iob.logic.boundaries.ActivityBoundary;
import iob.logic.boundaries.ActivityId;
import iob.logic.converters.ActivityConverter;
import iob.logic.exceptions.ActionNotFoundException;

@Service
public class ActivityServiceJpa implements ExtendedActivitiesService{
	private ActivityRepository activityCrud;
	private ActivityConverter activityConverter;
	private UserRepository userCrud;
	private InstanceRepository instanceCrud;
	private String domainName;


	@Autowired
	public ActivityServiceJpa(ActivityRepository activityCrud, ActivityConverter activityConverter,UserRepository userCrud,	InstanceRepository instanceCrud) {
		this.activityCrud = activityCrud;
		this.activityConverter = activityConverter;
		this.userCrud = userCrud;
		this.instanceCrud = instanceCrud;
	}


	@Value("${spring.application.name}")
	public void setConfigurableDomain(String domainName) {
		this.domainName = domainName;
	}




	@Override
	public List<ActivityBoundary> getAllActivities(String userDomain , String userEmail , int size, int page) {
		UserEntity user =  getUserForPermissions(userDomain, userEmail);
		
		if(user.getRole() != UserRoleEntity.admin)
			throw new RuntimeException("Permission denied");

		return this.activityCrud
				.findAll(PageRequest.of(page, size, Direction.ASC, "createdTimestemp" , "pk"))
				.stream()
				.map(this.activityConverter::toBoundary)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void deleteAllActivites(String userDomain, String userEmail) {
		UserEntity user =  getUserForPermissions(userDomain, userEmail);
		
		if(user.getRole() != UserRoleEntity.admin)
			throw new RuntimeException("Permission denied");

		this.activityCrud.deleteAll();
	}
	
	@Override
	@Transactional
	public Object invokeActivity(ActivityBoundary activityBoundary) {
		
		if(activityBoundary.getInvokedBy()==null || activityBoundary.getInvokedBy().getUserId() == null ) {
			throw new RuntimeException(); 
		}
		UserEntity user =  getUserForPermissions(
				activityBoundary.getInvokedBy().getUserId().getDomain(), 
				activityBoundary.getInvokedBy().getUserId().getEmail());

		if(user == null)
			throw new RuntimeException(); 
		if(user.getRole() != UserRoleEntity.player)
			throw new RuntimeException("Permission denied");

		if (activityBoundary.getInstance() == null || activityBoundary.getInstance().getInstanceId() == null) {
			throw new RuntimeException(); 
		}
		InstanceEntity instance = verifyEntityInstanceExists(
				activityBoundary.getInstance().getInstanceId().getDomain(),
				activityBoundary.getInstance().getInstanceId().getId());

		if (instance == null || instance.getActive() == false){
			throw new RuntimeException(); 
		}


		activityBoundary.setActivityId(new ActivityId(domainName, UUID.randomUUID().toString()));
		activityBoundary.setCreatedTimestamp(new Date());


		if (activityBoundary.getType() == null || activityBoundary.getType().isEmpty()) {

			throw new RuntimeException(); 
		}

		ActivityEntity entity = this.activityConverter.toEntity(activityBoundary);

		entity = this.activityCrud.save(entity);

		return this.activityConverter.toBoundary(entity);
	}
	
	public UserEntity getUserForPermissions (String userDomain, String userEmail) {
        Optional<UserEntity> op = this.userCrud
				.findById(userDomain+new UserEntity().seperate()+userEmail);
        if (op.isPresent()) {
			UserEntity entity = op.get();
			return entity;
		} else {
			throw new ActionNotFoundException("Could not find user by id: "+userDomain+new UserEntity().seperate()+userEmail);
		}
	}
	private InstanceEntity verifyEntityInstanceExists (String instanceDomain, String instanceId) {
		Optional<InstanceEntity> op = this.instanceCrud
				.findById(instanceDomain+new InstanceEntity().seperate()+instanceId);

		if (op.isPresent()) {
			InstanceEntity entity = op.get();
			return entity;
		} else {
			throw new ActionNotFoundException("Could not find instance by id: "+instanceDomain+instanceId);
		}
	}
	
	@Override
	public void deleteAllActivites() {
		throw new RuntimeException("deprecated method - use deleteAllActivities with user domain and email params");
	}

	@Override
	public List<ActivityBoundary> getAllActivities() {
		throw new RuntimeException("deprecated method - use getAllActivities with paginayion instead");
	}

}
