package iob.restApi;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import iob.logic.boundaries.ActivityBoundary;
import iob.logic.services.ActivityServiceJpa;


@RestController
public class ActivityController {
	private ActivityServiceJpa activityService;
	
	
	@Autowired
	public ActivityController(ActivityServiceJpa activityService) {
		this.activityService = activityService;
	}
	
	@RequestMapping(
			method = RequestMethod.POST,
			path = "/iob/activities",
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	public Object createActivity (
			@RequestBody ActivityBoundary activityBoundary) {
		
		
		
		return this.activityService.invokeActivity(activityBoundary); //verify active inside
	}
}
