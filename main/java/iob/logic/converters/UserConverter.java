package iob.logic.converters;

import java.util.UUID;

import org.springframework.stereotype.Component;

import iob.data.UserEntity;
import iob.data.UserRoleEntity;
import iob.logic.boundaries.UserBoundary;
import iob.logic.boundaries.UserId;
import iob.logic.boundaries.UserRole;

@Component
public class UserConverter {

	public UserBoundary toBoundary (UserEntity entity) {
		UserBoundary userBoundary = new UserBoundary();
		userBoundary.setAvatar(entity.getAvatar());
		userBoundary.setUserId(new UserId(entity.email(), entity.domain()));
		userBoundary.setUsername(entity.getUserName());
		userBoundary.setRole(this.toBoundary(entity.getRole()));
		return userBoundary;
	}

	public UserEntity toEntity (UserBoundary userBoundary) {
		UserEntity entity = new UserEntity();
		if (userBoundary.getUserId()!=null &&
				userBoundary.getUserId().getDomain()!=null &&
				userBoundary.getUserId().getEmail()!=null) {
			
			entity.setPk(userBoundary.getUserId().getDomain()+entity.seperate()+userBoundary.getUserId().getEmail());
		}
		if (userBoundary.getAvatar()!=null) {
			entity.setAvatar(userBoundary.getAvatar());
		}
		if (userBoundary.getUsername()!=null) {
			entity.setUserName(userBoundary.getUsername());
		}
		if (userBoundary.getRole()!=null) {
			entity.setRole(
					toEntity(userBoundary.getRole()));
		}
		return entity;
	}

	public UserRoleEntity toEntity (UserRole boundryRole) {
		if (boundryRole!=null) {
			String strRole = boundryRole.name().toLowerCase();
			UserRoleEntity rv = UserRoleEntity.valueOf(strRole);
			return rv;
		}
		return null;

	}

	public UserRole toBoundary (UserRoleEntity entityRole) {
		if (entityRole!=null) 
			return UserRole.valueOf(entityRole.name().toUpperCase());

		return null;

	}


}
