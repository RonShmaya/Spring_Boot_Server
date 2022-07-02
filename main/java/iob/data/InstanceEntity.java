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
 * 	private InstanceId instanceId;(instanceIdDomain,instanceIdId)
	private String type;
	private String name;
	private Boolean active;
	private Date createdTimestemp;
	private CreatedBy createdBy;(CrateByUserIdDomain, CrateByUserIdEmail)
	private Location location; (lat, lng)
	private Map<String, Object> instanceAttributs; (String instanceAttributs)
 */


@Document("INSTANCES")
public class InstanceEntity {
	@Transient
	private int DOMAIN_INDEX_IN_PK = 0;
	@Transient
	private int ID_INDEX_IN_PK = 1;
	@Transient
	private String PK_SEPERATE = " "; //the fields that contain in pk never will be with space
	@MongoId
	private String pk; //domain+id
	private String type;
	private String name;
	private boolean active;
	private Date createdTimestemp;
	private String CrateByUserIdDomain;
	private String CrateByUserIdEmail;
	private Double lat;
	private Double lng;
	private String instanceAttributs;


	public InstanceEntity() {
	}


	public String getPrimaryKey() {
		return pk;
	}


	public void setPrimaryKey(String primaryKey) {
		this.pk = primaryKey;
	}


	@Field(name = "TYPE")
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}

	@Field(name = "NAME")
	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}

	@Field(name = "ACTIVE")
	public boolean getActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	@Field(name = "DATE")
	public Date getCreatedTimestemp() {
		return createdTimestemp;
	}


	public void setCreatedTimestemp(Date createdTimestemp) {
		this.createdTimestemp = createdTimestemp;
	}


	public String instanceIdDomain() {
		return fromPk(DOMAIN_INDEX_IN_PK);
	}


	public String instanceIdId() {
		return fromPk(ID_INDEX_IN_PK); 
	}

	private String fromPk(int index_domain_or_id) {
		if(this.pk.isEmpty())
			return pk;
		String domain = pk.split(PK_SEPERATE)[index_domain_or_id]; 
		return domain;
	}
	public String seperate() {
		return PK_SEPERATE;
	}


	@Field(name = "CREATED_BY_DOMAIN")
	public String getCrateByUserIdDomain() {
		return CrateByUserIdDomain;
	}


	public void setCrateByUserIdDomain(String crateByUserIdDomain) {
		CrateByUserIdDomain = crateByUserIdDomain;
	}

	@Field(name = "CREATED_BY_EMAIL")
	public String getCrateByUserIdEmail() {
		return CrateByUserIdEmail;
	}


	public void setCrateByUserIdEmail(String crateByUserIdEmail) {
		CrateByUserIdEmail = crateByUserIdEmail;
	}

	@Field(name = "LAT")
	public Double getLat() {
		return lat;
	}


	public void setLat(Double lat) {
		this.lat = lat;
	}

	@Field(name = "LNG")
	public Double getLng() {
		return lng;
	}


	public void setLng(Double lng) {
		this.lng = lng;
	}


	public String getInstanceAttributs() {
		return instanceAttributs;
	}


	public void setInstanceAttributs(String instanceAttributs) {
		this.instanceAttributs = instanceAttributs;
	}


	public String getPk() {
		return pk;
	}


	public void setPk(String pk) {
		this.pk = pk;
	}


	public int getDOMAIN_INDEX_IN_PK() {
		return DOMAIN_INDEX_IN_PK;
	}


	public int getID_INDEX_IN_PK() {
		return ID_INDEX_IN_PK;
	}


	public String getPK_SEPERATE() {
		return PK_SEPERATE;
	}


	public void setDOMAIN_INDEX_IN_PK(int dOMAIN_INDEX_IN_PK) {
		DOMAIN_INDEX_IN_PK = dOMAIN_INDEX_IN_PK;
	}


	public void setID_INDEX_IN_PK(int iD_INDEX_IN_PK) {
		ID_INDEX_IN_PK = iD_INDEX_IN_PK;
	}


	public void setPK_SEPERATE(String pK_SEPERATE) {
		PK_SEPERATE = pK_SEPERATE;
	}
	
	

}
