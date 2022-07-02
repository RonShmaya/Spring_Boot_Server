package iob.logic.converters;

import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import iob.data.ActivityEntity;
import iob.logic.boundaries.ActivityBoundary;
import iob.logic.boundaries.ActivityId;
import iob.logic.boundaries.CreatedBy;
import iob.logic.boundaries.Instance;
import iob.logic.boundaries.InstanceId;
import iob.logic.boundaries.UserId;

@Component
public class ActivityConverter {

	private ObjectMapper jackson;

	@PostConstruct
	public void init () {
		this.jackson = new ObjectMapper();
	}

	public ActivityBoundary toBoundary (ActivityEntity entity) {
		ActivityBoundary activityBoundary = new ActivityBoundary();

		if (entity.getActivityAttributes()!=null) {
			activityBoundary.setActivityAttributes(this.toBoundaryFromJsonString(entity.getActivityAttributes()));
		}
		activityBoundary.setActivityId(new ActivityId(entity.activityDomain(), entity.activityId()));
		activityBoundary.setCreatedTimestamp(entity.getCreatedTimestamp());
		activityBoundary.setInstance(new Instance(new InstanceId(entity.getInstanceDomain(), entity.getInstanceId())));
		activityBoundary.setInvokedBy(new CreatedBy(new UserId(entity.getCreateByUserIdEmail(), entity.getCreateByUserIdDomain())));
		activityBoundary.setType(entity.getType());
		return activityBoundary;
	}

	public ActivityEntity toEntity (ActivityBoundary activityBoundary) {
		ActivityEntity entity = new ActivityEntity();
		if (activityBoundary.getActivityAttributes()!=null) {
			entity.setActivityAttributes(this.toEntity(activityBoundary.getActivityAttributes()));
		}
		if (activityBoundary.getActivityId()!=null && 
				activityBoundary.getActivityId().getId()!=null &&
				activityBoundary.getActivityId().getDomain()!=null) {
			
			entity.setPk(activityBoundary.getActivityId().getDomain()+entity.seperate()+activityBoundary.getActivityId().getId());
		}

		if (activityBoundary.getCreatedTimestamp()!=null) {
			entity.setCreatedTimestamp(activityBoundary.getCreatedTimestamp());
		}

		if (activityBoundary.getInstance()!=null) {
			if (activityBoundary.getInstance().getInstanceId()!=null) {
				if (activityBoundary.getInstance().getInstanceId().getDomain()!=null) {
					entity.setInstanceDomain(activityBoundary.getInstance().getInstanceId().getDomain());
				}
				if (activityBoundary.getInstance().getInstanceId().getId()!=null) {
					entity.setInstanceId(activityBoundary.getInstance().getInstanceId().getId());
				}
			}
		}

		if (activityBoundary.getInvokedBy()!=null) {
			if (activityBoundary.getInvokedBy().getUserId()!=null) {
				if (activityBoundary.getInvokedBy().getUserId().getDomain()!=null) {
					entity.setCreateByUserIdDomain(activityBoundary.getInvokedBy().getUserId().getDomain());
				}
				if (activityBoundary.getInvokedBy().getUserId().getEmail()!=null) {
					entity.setCreateByUserIdEmail(activityBoundary.getInvokedBy().getUserId().getEmail());
				}
			}
		}

		if (activityBoundary.getType()!=null) {
			entity.setType(activityBoundary.getType());
		}
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
