package iob.data;

import java.util.Date;

import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;


/*
 * 	private ActivityId activityId; (string domain, string id) 
	private String type; (string Type)
	private Instance instance; (instanceDomain, instanceId)
	private Date createdTimestamp; (createdTimestamp)
	private CreatedBy invokedBy; (CreatedByUserIdDomain, CreateByUserIdEmail)
	private Map<String, Object> activityAttributes; (String activityAttributes)
 */

@Document("ACTIVITIES")
public class ActivityEntity {
	@Transient
	private int DOMAIN_INDEX_IN_PK = 0;
	@Transient
	private int ID_INDEX_IN_PK = 1;
	@Transient
	private String PK_SEPERATE = " "; //the fields that contain in pk never will be with space
	
	@MongoId
	private String pk;
	private String type;
	private Date createdTimestamp;	
	private String CreateByUserIdDomain;
	private String CreateByUserIdEmail;
	private String instanceDomain;
	private String instanceId;
	private String activityAttributes;
	
	public ActivityEntity() {
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}
	
	@Field(name = "TYPE")
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Field(name = "DATE")
	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}


	public String activityDomain() {
			return fromPk(DOMAIN_INDEX_IN_PK); 
	}

	private String fromPk(int index_domain_or_id) {
		if(this.pk.isEmpty())
			return pk;
		String domain = pk.split(PK_SEPERATE)[index_domain_or_id]; 
		return domain;
	}

	public String activityId() {
		return fromPk(ID_INDEX_IN_PK); 
	}

	
	@Field(name = "USER_DOMAIN")
	public String getCreateByUserIdDomain() {
		return CreateByUserIdDomain;
	}

	public void setCreateByUserIdDomain(String createByUserIdDomain) {
		CreateByUserIdDomain = createByUserIdDomain;
	}
	@Field(name = "USER_EMAIL")
	public String getCreateByUserIdEmail() {
		return CreateByUserIdEmail;
	}

	public void setCreateByUserIdEmail(String createByUserIdEmail) {
		CreateByUserIdEmail = createByUserIdEmail;
	}
	@Field(name = "INSTANCE_DOMAIN")
	public String getInstanceDomain() {
		return instanceDomain;
	}

	public void setInstanceDomain(String instanceDomain) {
		this.instanceDomain = instanceDomain;
	}
	@Field(name = "INSTANCE_ID")
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}
	
	public String getActivityAttributes() {
		return activityAttributes;
	}

	public void setActivityAttributes(String activityAttributes) {
		this.activityAttributes = activityAttributes;
	}
	public String seperate() {
		return PK_SEPERATE;
	}

	public int getDOMAIN_INDEX_IN_PK() {
		return DOMAIN_INDEX_IN_PK;
	}

	public void setDOMAIN_INDEX_IN_PK(int dOMAIN_INDEX_IN_PK) {
		DOMAIN_INDEX_IN_PK = dOMAIN_INDEX_IN_PK;
	}

	public int getID_INDEX_IN_PK() {
		return ID_INDEX_IN_PK;
	}

	public void setID_INDEX_IN_PK(int iD_INDEX_IN_PK) {
		ID_INDEX_IN_PK = iD_INDEX_IN_PK;
	}

	public String getPK_SEPERATE() {
		return PK_SEPERATE;
	}

	public void setPK_SEPERATE(String pK_SEPERATE) {
		PK_SEPERATE = pK_SEPERATE;
	}

}
