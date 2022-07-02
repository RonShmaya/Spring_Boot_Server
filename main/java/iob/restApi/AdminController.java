package iob.restApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import iob.logic.boundaries.ActivityBoundary;
import iob.logic.boundaries.UserBoundary;
import iob.logic.services.ActivityServiceJpa;
import iob.logic.services.InstanceServiceJpa;
import iob.logic.services.UserServiceJpa;


@RestController
public class AdminController {
	private final String SIZE_DEFULT = "10";
	private final String PAGE_DEFULT = "0";
	private InstanceServiceJpa instanceService;
	private UserServiceJpa userService;
	private ActivityServiceJpa activityService;
	
	@Autowired
	public AdminController(ActivityServiceJpa activityService , InstanceServiceJpa instanceService ,
			UserServiceJpa userService) {
		
		this.activityService = activityService;
		this.instanceService = instanceService;
		this.userService = userService;

		
	}
	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/iob/admin/users")
	public void deleteAllUsers (
			@RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail) {
		
		
		this.userService.deleteAllUsers(userDomain , userEmail);
	}

	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/iob/admin/instances")
	public void deleteAllInstances (
			@RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail) {
		
		this.instanceService.deleteAllInstances(userDomain , userEmail);
	}

	@RequestMapping(
			method = RequestMethod.DELETE,
			path = "/iob/admin/activities")
	public void deleteAllActivities (
			@RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail) {
		
		
		this.activityService.deleteAllActivites(userDomain , userEmail);
	}

	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/admin/users",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public UserBoundary[] getAllUsersDetails (
			@RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail,
			@RequestParam(name="size", required = false, defaultValue = SIZE_DEFULT) int size,
			@RequestParam(name="page", required = false, defaultValue = PAGE_DEFULT) int page) {
		
		
		return this.userService.getAllUsers(userDomain, userEmail ,  size , page).toArray(new UserBoundary[0]);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/admin/activities",
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ActivityBoundary[] getAllActivitiesDetails (
			@RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail,
			@RequestParam(name="size", required = false, defaultValue = SIZE_DEFULT) int size,
			@RequestParam(name="page", required = false, defaultValue = PAGE_DEFULT) int page) {
		
		
		return this.activityService.getAllActivities(userDomain, userEmail , size , page).toArray(new ActivityBoundary[0]);
	}
	
}