package exort.apiserver.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.common.entity.ApiResponse;
import exort.apiserver.service.CommunityService;
import exort.apiserver.service.CommunityService.CoummunityMessage;

@RestController
@RequestMapping(path="/com")
public class CommunityController {

	@Autowired
	private CommunityService cmSvc;

	@PostMapping("/msg/{uid}")
	public ApiResponse postMessage(
			@RequestAttribute("id") int operatorId,
			@PathVariable("uid") int uid,
			@RequestBody String content){
		CoummunityMessage msg = new CoummunityMessage();
		msg.setSenderId(operatorId);
		msg.setContent(content);

		return cmSvc.postMessage(uid,msg);
	}

	@PostMapping("/msg/read/{mid}")
	public ApiResponse markMessageReadById(
			@RequestAttribute("id") int operatorId,
			@PathVariable("mid") int mid){
		return cmSvc.markMessageReadById(operatorId,mid);
	}

	@PostMapping("/msg/readall")
	public ApiResponse markAllMessageRead(
			@RequestAttribute("id") int operatorId){
		return cmSvc.markAllMessageRead(operatorId);
	}

	@PostMapping("/msg/drop/{mid}")
	public ApiResponse dropMessageById(
			@RequestAttribute("id") int operatorId,
			@PathVariable("mid") int mid){
		return cmSvc.dropMessageById(operatorId,mid);
	}

	@PostMapping("/msg/dropall")
	public ApiResponse dropAllMessage(
			@RequestAttribute("id") int operatorId){
		return cmSvc.dropAllMessage(operatorId);
	}

	@GetMapping("/msg")
	public ApiResponse getMessage(
			@RequestAttribute("id") int operatorId){
		return cmSvc.getMessageForUser(operatorId);
	}

	@PostMapping("/sub")
	public ApiResponse commitSubscription(
			@RequestAttribute("id") int operatorId,
			@RequestBody List<String> assoIds){
		return cmSvc.addToSubscribed(operatorId,assoIds);
	}

	@DeleteMapping("/sub")
	public ApiResponse removeSubscription(
			@RequestAttribute("id") int operatorId,
			@RequestBody List<String> assoIds){
		return cmSvc.removeFromSubscribed(operatorId,assoIds);
	}

	@GetMapping("/sub")
	public ApiResponse listSubscribed(
			@RequestAttribute("id") int operatorId){
		return cmSvc.listSubscribed(operatorId);
	}

}
