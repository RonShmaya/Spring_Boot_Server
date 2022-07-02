package iob.data;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import org.springframework.data.annotation.Transient;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document("USERS")
public class UserEntity {
	@Transient
	private int DOMAIN_INDEX_IN_PK = 0;
	@Transient
	private int EMAIL_INDEX_IN_PK = 1;
	@Transient
	private String PK_SEPERATE = " "; //the fields that contain in pk never will be with space
	
	@MongoId
	private String pk;//Primary Key
	private UserRoleEntity role;
	private String userName;
	private String avatar;
	
	public UserEntity() {
		
	}
		
	public UserEntity( UserRoleEntity role, String userName, String avatar) {
		this.role = role;
		this.userName = userName;
		this.avatar = avatar;
	}
	
	
	public String getPk() {
		return pk;
	}

	public void setPk(String id) {
		this.pk = id;
	}

	public String domain() {
		return getFromPk(DOMAIN_INDEX_IN_PK);
	}


	public String email() {
		return getFromPk(EMAIL_INDEX_IN_PK);
	}


	private String getFromPk(int index_domain_or_id) {
		if(this.pk.isEmpty())
			return pk;
		String domain = pk.split(PK_SEPERATE)[index_domain_or_id]; 
		return domain;
	}
	public String seperate() {
		return PK_SEPERATE;
	}

	
	@Field(name = "ROLE")
	public UserRoleEntity getRole() {
		return role;
	}

	public void setRole(UserRoleEntity role) {
		this.role = role;
	}
	@Field(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Field(name = "AVATAR")
	public String getAvatar() {
		return avatar;
	}


	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getDOMAIN_INDEX_IN_PK() {
		return DOMAIN_INDEX_IN_PK;
	}

	public void setDOMAIN_INDEX_IN_PK(int dOMAIN_INDEX_IN_PK) {
		DOMAIN_INDEX_IN_PK = dOMAIN_INDEX_IN_PK;
	}

	public int getEMAIL_INDEX_IN_PK() {
		return EMAIL_INDEX_IN_PK;
	}

	public void setEMAIL_INDEX_IN_PK(int eMAIL_INDEX_IN_PK) {
		EMAIL_INDEX_IN_PK = eMAIL_INDEX_IN_PK;
	}

	public String getPK_SEPERATE() {
		return PK_SEPERATE;
	}

	public void setPK_SEPERATE(String pK_SEPERATE) {
		PK_SEPERATE = pK_SEPERATE;
	}
	
	
	
}
