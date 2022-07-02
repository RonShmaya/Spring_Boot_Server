package iob.restApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.logic.boundaries.UserBoundary;
import iob.logic.boundaries.UserId;
import iob.logic.services.UserServiceJpa;

@RestController
public class UsersController {
	private UserServiceJpa userService;

	@Autowired
	public UsersController(UserServiceJpa userService) {
		this.userService = userService;

	}

	@RequestMapping(
			method = RequestMethod.POST,
			path = "/iob/users",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary createUser (@RequestBody NewUserBoundary newUserBoundary) {
		UserBoundary userBoundry=new UserBoundary(newUserBoundary.getRole(),
				newUserBoundary.getUsername(),
				newUserBoundary.getAvatar(),
				new UserId(newUserBoundary.getEmail(), null)
				);

		return userService.createUser(userBoundry);
	}


	@GetMapping(
//			method = RequestMethod.GET,
			path = "/iob/users/login/{userDomain}/{userEmail}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary getUserDetails (@PathVariable("userDomain") String userDomain ,@PathVariable("userEmail") String userEmail) {
		return userService.login(userDomain, userEmail);
	}

	@RequestMapping(
			method = RequestMethod.PUT,
			path = "/iob/users/{userDomain}/{userEmail}",
			consumes = MediaType.APPLICATION_JSON_VALUE)

	public void updateUserDetails (@PathVariable("userDomain") String userDomain ,@PathVariable("userEmail") String userEmail,@RequestBody UserBoundary userBoundary) {
		userService.updateUser(userDomain, userEmail, userBoundary);
	}


}
