package exort.apiserver.controller;

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
    public Response updateActivity(@RequestBody Activity activity, @PathVariable("id") String id){
        return service.updateActivity(activity,id);
    }

    @GetMapping("/")
    public Response getActivities(@RequestBody Select select, @PathParam(value = "pagesize") int pagesize, @PathParam(value = "pagenum")int pagenum, @PathParam(value = "sortby") int sortby){
        return service.getActivities(select,pagesize,pagenum,sortby);
    }

    @PutMapping("/{id}/state")
    public Response publishActivity(@PathVariable("id")String id, @RequestBody Request request){
//        System.out.println(request.getType());
        return service.publishActivity(id, request);
    }

    @PostMapping("/{id}/participants")
    public Response addParticipants(@PathVariable("id") String id, @RequestBody Request request){
        System.out.println(request.getParticipantIds());
        return service.addParticipants(id, request);
    }

    @PostMapping("/{id}/realparticipants")
    public Response addRealParticipants(@PathVariable("id")String id, @RequestBody Request request){
        return service.addRealParticipants(id, request);
    }

    @DeleteMapping("/{id}/participants")
    public Response deleteParticipants(@PathVariable("id")String id, @RequestBody Request request){
        return service.deleteParticipants(id, request);
    }

    @GetMapping("/{id}/participants")
    public Response getActivityParticipants(@PathParam(value = "pagesize")int pagesize,@PathParam(value = "pagenum")int pagenum,@PathVariable("id")String id,@RequestBody Request request){
        return service.getActivityParticipants(pagesize,pagenum,id, request);
    }

    @GetMapping("/{id}/realparticipants")
    public Response getActivityRealParticipants(@PathParam(value = "pagesize")int pagesize,@PathParam(value = "pagenum")int pagenum,@PathVariable("id")String id,@RequestBody Request request){
        return service.getActivityRealParticipants(pagesize,pagenum,id, request);
    }

    @PostMapping(value = "/callback/acceptsignup")
    public Response acceptSignup(@RequestBody Operation operation){
        return service.acceptSignup(operation);
    }

    @GetMapping(value = "/{id}")
    public Response getActivity(@PathVariable("id")String id){
        return service.getActivity(id);
    }

}
