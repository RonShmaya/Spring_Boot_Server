package iob.logic.services;


import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import iob.data.UserRepository;
import iob.data.UserEntity;
import iob.logic.ExtendedUsersService;
import iob.logic.boundaries.UserBoundary;
import iob.logic.boundaries.UserRole;
import iob.logic.converters.UserConverter;
import iob.logic.exceptions.ActionNotFoundException;
import iob.logic.exceptions.FoundException;

/*
 * 	private String email;		//Primary Key
	private String domain;
	private UserRole role;
	private String userName;
	private String avatar;
 */

@Service
public class UserServiceJpa implements ExtendedUsersService{

	private UserRepository userCrud;
	private UserConverter userConverter;
	private String domainName;
	
	@Autowired
	public UserServiceJpa(
			UserRepository userCrud,
			UserConverter userConverter) {
		this.userCrud = userCrud;
		this.userConverter = userConverter;
	}

	@Value("${spring.application.name}")
	public void setConfigurableMessage(String domainName) {
		this.domainName = domainName;
	}

	
	@Override
	@Transactional 
	public UserBoundary createUser(UserBoundary userBoundaryToStore) {
		if(userBoundaryToStore.getUserId() == null
				|| !this.isEmailValid(userBoundaryToStore.getUserId().getEmail()) 
				|| userBoundaryToStore.getUsername() == null
				|| userBoundaryToStore.getUsername().isEmpty()
				|| userBoundaryToStore.getAvatar() == null
				|| userBoundaryToStore.getAvatar().isEmpty()
				|| userBoundaryToStore.getRole() == null)
			throw new RuntimeException();
		
		
		if(this.verifySpecificEntityUser(domainName, userBoundaryToStore.getUserId().getEmail())) 
			throw new FoundException();
		
		
		userBoundaryToStore.getUserId().setDomain(domainName);
		

		UserEntity entity = this.userConverter.toEntity(userBoundaryToStore);
		// store entity in database using INSERT genereted by Hibernate + Spring Data JPA
		entity = this.userCrud.save(entity);

		return this.userConverter.toBoundary(entity);
	}

	@Override
	@Transactional (readOnly = true)
	public UserBoundary login(String userDomain, String userEmail) {
		return this.userConverter
				.toBoundary(this.getSpecificEntityUser(userDomain, userEmail)); // throw if needed
	}
	
	public UserEntity getSpecificEntityUser (String userDomain, String userEmail) {
        Optional<UserEntity> op = this.userCrud
				.findById(userDomain+new UserEntity().seperate()+userEmail);
        if (op.isPresent()) {
			UserEntity entity = op.get();
			return entity;
		} else {
			throw new ActionNotFoundException("Could not find user by id: "+userDomain+new UserEntity().seperate()+userEmail);
		}
	}
	public boolean verifySpecificEntityUser (String userDomain, String userEmail) {
		Optional<UserEntity> op = this.userCrud
				.findById(userDomain+new UserEntity().seperate()+userEmail);

		if (op.isPresent()) {
			return true;
		} 
		return false;		
	}

	@Override
	@Transactional
	public UserBoundary updateUser(String userDomain, String userEmail, UserBoundary update) {
		UserEntity entity = this.getSpecificEntityUser(userDomain, userEmail);
		
		if (update.getAvatar()!=null)
			entity.setAvatar(update.getAvatar());
		
		if (update.getRole()!=null) {
			entity.setRole(this.userConverter.toEntity(update.getRole()));
		}
		
		if (update.getUserId()!=null) {
			if (update.getUserId().getDomain()!=null) {
				//do nothing
			}
			if (update.getUserId().getEmail()!=null) {
				//do nothing
			}
		}
		
		if (update.getUsername()!=null) {
			entity.setUserName(update.getUsername());
		}
		
		entity = this.userCrud.save(entity);
		
		return this.userConverter.toBoundary(entity);
	}

	
	@Override
	@Transactional(readOnly = true)
	public List<UserBoundary> getAllUsers(String userDomain, String userEmail, int size, int page) {
		
		UserBoundary user =  login(userDomain, userEmail); //get user
		if(user.getRole() != UserRole.ADMIN)
			throw new RuntimeException("Permission denied");
		
		return this.userCrud
		.findAll(PageRequest.of(page, size, Direction.ASC, "userName" , "id"))
		.stream()
		.map(this.userConverter::toBoundary)
		.collect(Collectors.toList());
	}
	

	private boolean isEmailValid(String email)
	{
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
				"[a-zA-Z0-9_+&*-]+)*@" +
				"(?:[a-zA-Z0-9-]+\\.)+[a-z" +
				"A-Z]{2,7}$";
		if(email == null)
			return false;

		Pattern pat = Pattern.compile(emailRegex);
		return pat.matcher(email).matches();
	}

	@Override
	@Transactional
	public void deleteAllUsers(String userDomain, String userEmail) {
		UserBoundary user =  login(userDomain, userEmail); //get user
		if(user.getRole() != UserRole.ADMIN)
			throw new RuntimeException("Permission denied");
		
		this.userCrud.deleteAll();
		
	}
	
	@Override
	public void deleteAllUsers() {
		throw new RuntimeException("deprecated method - use getDeleteAll Users with user domain and email params");
	}
	
	@Override
	public List<UserBoundary> getAllUsers() {
			throw new RuntimeException("deprecated method - use getAllUsers with paginayion instead");
	}



}
