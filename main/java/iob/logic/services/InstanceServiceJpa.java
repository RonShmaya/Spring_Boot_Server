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
import iob.data.InstanceRepository;
import iob.data.UserEntity;
import iob.data.UserRepository;
import iob.data.UserRoleEntity;
import iob.data.InstanceEntity;
import iob.logic.ExtendedInstancesService;
import iob.logic.boundaries.InstanceBoundary;
import iob.logic.boundaries.InstanceId;
import iob.logic.converters.InstanceConverter;
import iob.logic.exceptions.ActionNotFoundException;

@Service
public class InstanceServiceJpa implements ExtendedInstancesService{

	private InstanceRepository instanceCrud;
	private InstanceConverter instanceConverter;
	private UserRepository userCrud;
	private String domainName;

	@Autowired
	public InstanceServiceJpa(InstanceRepository instanceCrud, InstanceConverter instanceConverter, UserRepository userCrud ) {
		this.instanceCrud = instanceCrud;
		this.instanceConverter = instanceConverter;
		this.userCrud = userCrud;
		
	}

	@Value("${spring.application.name}")
	public void setConfigurableMessage(String domainName) {
		this.domainName = domainName;
	}

	@Override
	@Transactional (readOnly = true)
	public InstanceBoundary getSpecificInstance(String instanceDomain, String instanceId) {
		return this.instanceConverter
				.toBoundary(this.getSpecificEntityInstance(instanceDomain, instanceId)); //throw if needed

	}

	private InstanceEntity getSpecificEntityInstance (String instanceDomain, String instanceId) {
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
	@Transactional (readOnly = true)
	public List<InstanceBoundary> getInstancesByName(String name, int size, int page) {
		return this.instanceCrud
				.findAllByName(name, PageRequest.of(page, size, Direction.ASC, "createdTimestemp" , "pk"))
				.stream()
				.map(this.instanceConverter::toBoundary)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional (readOnly = true)
	public List<InstanceBoundary> getInstancesByType(String type, int size, int page) {
		return this.instanceCrud
				.findAllByType(type, PageRequest.of(page, size, Direction.ASC, "createdTimestemp" , "pk"))
				.stream()
				.map(this.instanceConverter::toBoundary)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional (readOnly = true)
	public List<InstanceBoundary> getInstancesByNear(double lat, double lng, double distance, int size, int page) {
		return this.instanceCrud
		.findAllByLatBetweenAndLngBetween(lat - distance, lat + distance, lng - distance, lng + distance,
				PageRequest.of(page, size, Direction.ASC, "createdTimestemp" , "pk"))
		.stream()
		.map(this.instanceConverter::toBoundary)
		.collect(Collectors.toList());
	}

	@Override
	@Transactional (readOnly = true)
	public List<InstanceBoundary> getAllInstances(int size, int page) {
		return this.instanceCrud
		.findAll(PageRequest.of(page, size, Direction.ASC, "createdTimestemp" , "pk"))
		.stream()
		.map(this.instanceConverter::toBoundary)
		.collect(Collectors.toList());
	}

	@Override
	public InstanceBoundary getSpecificInstanceMustBeActive(String instanceDomain, String instanceId) {
		InstanceBoundary instanceBoundary =  getSpecificInstance(instanceDomain , instanceId);
		
		if(instanceBoundary.getActive() == false)
			throw new ActionNotFoundException("must be active");
		
		return instanceBoundary;
	}
	
	public List<InstanceBoundary> getAllInstancesMustBeActive(int size, int page) {
		return this.instanceCrud
		.findAllByActive(true , PageRequest.of(page, size, Direction.ASC, "createdTimestemp" , "pk"))
		.stream()
		.map(this.instanceConverter::toBoundary)
		.collect(Collectors.toList());
	}

	public List<InstanceBoundary> getInstancesByNameMustBeActive(String name, int size, int page) {
		return this.instanceCrud
				.findAllByNameAndActive(name, true,PageRequest.of(page, size, Direction.ASC, "createdTimestemp" , "pk"))
				.stream()
				.map(this.instanceConverter::toBoundary)
				.collect(Collectors.toList());
	}
	
	@Override
	public List<InstanceBoundary> getInstancesByTypeMustBeActive(String type, int size, int page) {
		return this.instanceCrud
				.findAllByTypeAndActive(type, true, PageRequest.of(page, size, Direction.ASC, "createdTimestemp" , "pk"))
				.stream()
				.map(this.instanceConverter::toBoundary)
				.collect(Collectors.toList());
	}

	@Override
	public List<InstanceBoundary> getInstancesByNearMustBeActive(double lat, double lng, double distance, int size,
			int page) {
		
		return this.instanceCrud
				.findAllByLatBetweenAndLngBetweenAndActive(lat - distance, lat + distance, lng - distance, lng + distance, true,
						PageRequest.of(page, size, Direction.ASC, "createdTimestemp" , "pk"))
				.stream()
				.map(this.instanceConverter::toBoundary)
				.collect(Collectors.toList());
	}
	
	@Override
	@Transactional
	public void deleteAllInstances(String userDomain, String userEmail) {
		UserEntity user =  getUserForPermissions(userDomain, userEmail);
		
		if(user.getRole() != UserRoleEntity.admin)
			throw new RuntimeException("Permission denied");
		
		this.instanceCrud.deleteAll();
		
	}
	@Override
	@Transactional
	public InstanceBoundary updateInstance(String userDomain ,String userEmail , String instanceDomain, String instanceId, InstanceBoundary update) {
		UserEntity user =  getUserForPermissions(userDomain, userEmail);
		
		if(user.getRole() != UserRoleEntity.manager)
			throw new RuntimeException("Permission denied");
		
		InstanceEntity entity = this.getSpecificEntityInstance(instanceDomain, instanceId);
		if (update.getType()!=null) {
			entity.setType(update.getType());
		}

		if (update.getName()!=null) {
			entity.setName(update.getName());
		}

		if (update.getActive()!=null) {
			entity.setActive(update.getActive());
		}

		if (update.getCreatedTimestamp()!=null) {
			//do nothing
		}

		if (update.getCreatedBy()!=null) {
			//do nothing
		}

		if (update.getLocation()!=null) {
			if (update.getLocation().getLat()!=null) {
				entity.setLat(update.getLocation().getLat());
			}
			if (update.getLocation().getLng()!=null) {
				entity.setLng(update.getLocation().getLng());
			}
		}

		if (update.getInstanceAttributes()!=null) {
			entity.setInstanceAttributs(this.instanceConverter.toEntity(update.getInstanceAttributes()));
		}

		if (update.getInstanceId()==null) {
			//do nothing
		}

		entity = this.instanceCrud.save(entity);

		return this.instanceConverter.toBoundary(entity);
	}
	
	@Override
	public InstanceBoundary getInstanceHandler(String userDomain, String userEmail, String instanceDomain, String instanceId) {
		UserEntity user =  getUserForPermissions(userDomain, userEmail);
		
		if(user.getRole() != UserRoleEntity.manager && user.getRole() != UserRoleEntity.player)
			throw new RuntimeException("Permission denied"); 
		
		if(user.getRole() == UserRoleEntity.player)// throw 404 if not active
			return this.getSpecificInstanceMustBeActive(instanceDomain, instanceId);
		
		return this.getSpecificInstance(instanceDomain, instanceId);
	}
	@Override
	public List<InstanceBoundary> getAllInstancesHandler(String userDomain, String userEmail, int size, int page) {
		UserEntity user =  getUserForPermissions(userDomain, userEmail);
		
		if(user.getRole() != UserRoleEntity.manager && user.getRole() != UserRoleEntity.player)
			throw new RuntimeException("Permission denied"); 
		
		if(user.getRole() == UserRoleEntity.player)
			return this.getAllInstancesMustBeActive(size, page);
		
		return this.getAllInstances(size, page);
	}
	@Override
	public List<InstanceBoundary> getInstancesByNameHandler(String userDomain, String userEmail, String name, int size,
			int page) {
		
		UserEntity user =  getUserForPermissions(userDomain, userEmail);
		
		if(user.getRole() != UserRoleEntity.manager && user.getRole() != UserRoleEntity.player)
			throw new RuntimeException("Permission denied"); 
		
		if(user.getRole() == UserRoleEntity.player)
			return this.getInstancesByNameMustBeActive(name, size, page);
		
		return this.getInstancesByName(name, size, page);
			
	}

	@Override
	public List<InstanceBoundary> getInstancesByTypeHandler(String userDomain, String userEmail, String type, int size,
			int page) {
		
		UserEntity user =  getUserForPermissions(userDomain, userEmail);
		if(user.getRole() != UserRoleEntity.manager && user.getRole() != UserRoleEntity.player)
			throw new RuntimeException("Permission denied"); 
		
		if(user.getRole() == UserRoleEntity.player)
			return this.getInstancesByTypeMustBeActive(type, size, page);
		
		return this.getInstancesByType(type, size, page);
	}

	@Override
	public List<InstanceBoundary> getInstancesByLocationHandler(String userDomain, String userEmail, double lat, double lng,
			double distance, int size, int page) {
		
		UserEntity user =  getUserForPermissions(userDomain, userEmail);
		if(user.getRole() != UserRoleEntity.manager && user.getRole() != UserRoleEntity.player)
			throw new RuntimeException("Permission denied"); 
		
		
		if(user.getRole() == UserRoleEntity.player)
			return this.getInstancesByNearMustBeActive(lat, lng, distance, size, page);
		
		return this.getInstancesByNear(lat, lng, distance, size, page);
	}

	@Override
	@Transactional
	public InstanceBoundary createInstance(InstanceBoundary instanceBoundary) {

		if(instanceBoundary.getCreatedBy() == null || instanceBoundary.getCreatedBy().getUserId() == null
				|| instanceBoundary.getCreatedBy().getUserId().getDomain() == null
				|| instanceBoundary.getCreatedBy().getUserId().getEmail() == null)
			throw new ActionNotFoundException();
		
		UserEntity user =  getUserForPermissions(
				instanceBoundary.getCreatedBy().getUserId().getDomain(),
				instanceBoundary.getCreatedBy().getUserId().getEmail());

		if(user == null)
			throw new RuntimeException();
		if(user.getRole() != UserRoleEntity.manager)
			throw new RuntimeException("Permission denied");

		if (instanceBoundary.getName() == null || instanceBoundary.getName().isEmpty() 
				|| instanceBoundary.getType() == null || instanceBoundary.getType().isEmpty())  {
			throw new RuntimeException();
		}
		instanceBoundary.setCreatedTimestamp(new Date());

		if (instanceBoundary.getActive()==null) {
			instanceBoundary.setActive(false);
		}
		
		instanceBoundary.setInstanceId(new InstanceId(domainName, UUID.randomUUID().toString()));

		InstanceEntity entity = this.instanceConverter.toEntity(instanceBoundary);

		// store entity in database using INSERT genereted by Hibernate + Spring Data JPA
		entity = this.instanceCrud.save(entity);

		return this.instanceConverter.toBoundary(entity);
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

	@Override
	public InstanceBoundary updateInstance( String instanceDomain, String instanceId, InstanceBoundary update) {
		throw new RuntimeException("deprecated method - use updateInstance with user domain and email params");
	}
	
	@Override
	public List<InstanceBoundary> getAllInstances() {
		throw new RuntimeException("deprecated method - use getAllInstances with paginayion instead");
	}
	@Override
	public void deleteAllInstances() {
		throw new RuntimeException("deprecated method - use deletetAllInstances with user domain and email params");
	}

}
