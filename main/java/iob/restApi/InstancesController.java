package iob.restApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import iob.logic.boundaries.InstanceBoundary;
import iob.logic.services.InstanceServiceJpa;



@RestController
public class InstancesController {
	

	private final String SIZE_DEFULT = "10";
	private final String PAGE_DEFULT = "0";
	private InstanceServiceJpa instanceService;
	
	@Autowired
	public InstancesController(InstanceServiceJpa instanceService) {
		this.instanceService = instanceService;
	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = "/iob/instances",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public InstanceBoundary createInstance(
			@RequestBody InstanceBoundary instanceBoundary) {
		
		
		return instanceService.createInstance(instanceBoundary);
	}
	
	@RequestMapping(
			method = RequestMethod.PUT,
			path = "/iob/instances/{instanceDomain}/{instanceId}",
			consumes = MediaType.APPLICATION_JSON_VALUE)
	
	public void updateInstanceDetails (
			@PathVariable("instanceDomain") String instanceDomain, 
			@PathVariable("instanceId") String instanceId,
			@RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail,
			@RequestBody InstanceBoundary instanceBoundary) {
		
		
		instanceService.updateInstance(userDomain , userEmail , instanceDomain, instanceId, instanceBoundary);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/instances/{instanceDomain}/{instanceId}",
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public InstanceBoundary getInstanceDetails (
			@PathVariable("instanceDomain") String instanceDomain ,
			@PathVariable("instanceId") String instanceId,
			@RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail) {
		

		return instanceService.getInstanceHandler(userDomain, userEmail, instanceDomain, instanceId);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/instances",
			produces = MediaType.APPLICATION_JSON_VALUE)

	public InstanceBoundary[] getAllInstancesDetails (
			@RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail,
			@RequestParam(name="size", required = false, defaultValue = SIZE_DEFULT) int size,
			@RequestParam(name="page", required = false, defaultValue = PAGE_DEFULT) int page) {
		

		return instanceService.getAllInstancesHandler(userDomain, userEmail, size, page).toArray(new InstanceBoundary[0]);
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/instances/search/byName/{name}",
			produces = MediaType.APPLICATION_JSON_VALUE)

	public InstanceBoundary[] searchInstancesByName (
			@PathVariable("name") String name,
			@RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail,
			@RequestParam(name="size", required = false, defaultValue = SIZE_DEFULT) int size,
			@RequestParam(name="page", required = false, defaultValue = PAGE_DEFULT) int page) {
		
		
		return instanceService.getInstancesByNameHandler(userDomain, userEmail ,name, size, page)
				.toArray(new InstanceBoundary[0]);
		
	}
	
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/instances/search/byType/{type}",
			produces = MediaType.APPLICATION_JSON_VALUE)

	public InstanceBoundary[] searchInstancesByType (
			@PathVariable("type") String type,
			@RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail,
			@RequestParam(name="size", required = false, defaultValue = SIZE_DEFULT) int size,
			@RequestParam(name="page", required = false, defaultValue = PAGE_DEFULT) int page) {
		
		
		return instanceService.getInstancesByTypeHandler(userDomain, userEmail , type, size, page)
				.toArray(new InstanceBoundary[0]);
	}
	
	//need to check
	@RequestMapping(
			method = RequestMethod.GET,
			path = "/iob/instances/search/near/{lat}/{lng}/{distance}",
			produces = MediaType.APPLICATION_JSON_VALUE)

	public InstanceBoundary[] searchInstancesByType (
			@PathVariable("lat") double lat,
			@PathVariable("lng") double lng,
			@PathVariable("distance") double distance,
			@RequestParam(name="userDomain", required = true) String userDomain,
			@RequestParam(name="userEmail", required = true) String userEmail,
			@RequestParam(name="size", required = false, defaultValue = SIZE_DEFULT) int size,
			@RequestParam(name="page", required = false, defaultValue = PAGE_DEFULT) int page) {
		
		
		return instanceService.getInstancesByLocationHandler(userDomain, userEmail , lat, lng, distance, size, page)
				.toArray(new InstanceBoundary[0]);
	}
	
}
