package exort.apiserver.controller;
import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exort.apiserver.service.ActivityManagerService;
import exort.apiserver.service.ActivityManagerService.*;

@RestController
@RequestMapping(path="/activities")
public class ActivityManagerController {

	@Autowired 
	private ActivityManagerService service;

	@PostMapping("/")
    public Response createNewActivity(@RequestBody Activity activity){
		return service.createNewActivity(activity);
	}

	@PutMapping("/{id}")
    public Response updateActivity(@RequestBody Activity activity,@PathVariable("id") String id){
		return service.updateActivity(activity,id);
	}

	@GetMapping("/")
    public Response getActivities(@RequestBody Select select, @PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum")int pagenum, @PathParam(value = "sortby") int sortby){
		return service.getActivities(select,pagesize,pagenum,sortby);
	}

	@PatchMapping("/{id}")
    public Response publishActivity(@PathVariable("id")String id, @RequestBody String type){
		return service.publishActivity(id,type);
	}

	@PostMapping("/{id}/participants")
    public Response addParticipants(@PathVariable("id") String id, @RequestBody List<Integer> participantIds){
		return service.addParticipants(id,participantIds);
	}

	@PostMapping("/{id}/realparticipants")
    public Response addRealParticipants(@PathVariable("id")String id, @RequestBody List<Integer> realParticipantIds){
		return service.addRealParticipants(id,realParticipantIds);
	}

	@DeleteMapping("/{id}/participants")
    public Response deleteParticipants(@PathVariable("id")String id, @RequestBody List<Integer> participantIds){
		return service.deleteParticipants(id,participantIds);
	}

	@GetMapping("/{id}/participants")
    public Response getActivityParticipants(@PathParam(value = "pagesize")int pagesize,@PathParam(value = "pagenum")int pagenum,@PathVariable("id")String id,@RequestBody List<Integer> participantIds){
		return service.getActivityParticipants(pagesize,pagenum,id,participantIds);
	}

	@GetMapping("/{id}/realparticipants")
    public Response getActivityRealParticipants(@PathParam(value = "pagesize")int pagesize,@PathParam(value = "pagenum")int pagenum,@PathVariable("id")String id,@RequestBody List<Integer> participantIds){
		return service.getActivityRealParticipants(pagesize,pagenum,id,participantIds);
	}

    @PostMapping(value = "/callback/acceptsignup")
	public Response acceptSignup(@RequestBody Operation operation){
		return service.acceptSignup(operation);
	}
	
    @GetMapping(value = "/{id}")
    public Response getActivity(@PathParam("id")String id){
		return service.getActivity(id);
	}

}
