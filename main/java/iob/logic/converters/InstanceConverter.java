package iob.logic.converters;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import iob.data.InstanceEntity;
import iob.logic.boundaries.CreatedBy;
import iob.logic.boundaries.InstanceBoundary;
import iob.logic.boundaries.InstanceId;
import iob.logic.boundaries.Location;
import iob.logic.boundaries.UserId;

@Component
public class InstanceConverter {

	private ObjectMapper jackson;

	@PostConstruct
	public void init () {
		this.jackson = new ObjectMapper();
	}

	public InstanceBoundary toBoundary (InstanceEntity entity) {
		InstanceBoundary instanceBoundary = new InstanceBoundary();
		instanceBoundary.setActive(entity.getActive());
		instanceBoundary.setCreatedBy(new CreatedBy(new UserId(entity.getCrateByUserIdEmail(), entity.getCrateByUserIdDomain())));
		instanceBoundary.setCreatedTimestamp(entity.getCreatedTimestemp());
		if (entity.getInstanceAttributs()!=null) {
			instanceBoundary.setInstanceAttributes(this.toBoundaryFromJsonString(entity.getInstanceAttributs()));
		}
		instanceBoundary.setInstanceId(new InstanceId(entity.instanceIdDomain(), entity.instanceIdId()));
		instanceBoundary.setLocation(new Location(entity.getLat(), entity.getLng()));
		instanceBoundary.setName(entity.getName());
		instanceBoundary.setType(entity.getType());
		return instanceBoundary;
	}

	public InstanceEntity toEntity (InstanceBoundary instanceBoundary) {
		InstanceEntity entity = new InstanceEntity();
		if (instanceBoundary.getActive())
			entity.setActive(instanceBoundary.getActive());
		if (instanceBoundary.getCreatedBy()!=null) {
			if (instanceBoundary.getCreatedBy().getUserId()!=null) {
				if (instanceBoundary.getCreatedBy().getUserId().getEmail()!=null) {
					entity.setCrateByUserIdEmail(instanceBoundary.getCreatedBy().getUserId().getEmail());
				}
				if (instanceBoundary.getCreatedBy().getUserId().getDomain()!=null) {
					entity.setCrateByUserIdDomain(instanceBoundary.getCreatedBy().getUserId().getDomain());
				}
			}
		}
		if (instanceBoundary.getCreatedTimestamp()!=null) {
			entity.setCreatedTimestemp(instanceBoundary.getCreatedTimestamp());
		}
		if (instanceBoundary.getInstanceAttributes()!=null) {
			entity.setInstanceAttributs(this.toEntity(instanceBoundary.getInstanceAttributes()));
		}
		if (instanceBoundary.getInstanceId()!=null &&
				instanceBoundary.getInstanceId().getDomain()!=null &&
				instanceBoundary.getInstanceId().getId()!=null) {
			
			entity.setPrimaryKey(instanceBoundary.getInstanceId().getDomain()+entity.seperate()+instanceBoundary.getInstanceId().getId());

		}	
		if (instanceBoundary.getLocation()!=null) {
			if (instanceBoundary.getLocation().getLat()!=null) {
				entity.setLat(instanceBoundary.getLocation().getLat());
			}
			if (instanceBoundary.getLocation().getLng()!=null) {
				entity.setLng(instanceBoundary.getLocation().getLng());
			}
		}
		entity.setName(instanceBoundary.getName());
		entity.setType(instanceBoundary.getType());
		//entity.setPrimaryKey(instanceBoundary.getInstanceId().getDomain()+instanceBoundary.getInstanceId().getId());
		return entity;
	}

	public String toEntity (Map<String, Object> object) {
		try {
			return this.jackson.writeValueAsString(object);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Map<String, Object> toBoundaryFromJsonString (String json) {
		try {
			return this.jackson.readValue(json, Map.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
