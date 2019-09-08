package exort.apiserver.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.assomgr.service.AssociationManagerService;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.errorhandler.ApiError;
import exort.apiserver.service.CommunityService;
import exort.apiserver.service.CommunityService.CommunityMessage;

@RestController
@RequestMapping(path="/com")
public class CommunityController {

	@Autowired
	private CommunityService cmSvc;
	@Autowired
	private AssociationManagerService assoSvc;

	private final String POST_MSG_CONTENT_KEY = "content";

	@PostMapping("/msg/{uid}")
	public ApiResponse postMessage(
			@RequestAttribute(name="id", required=false) Integer operatorId,
			@PathVariable("uid") int uid,
			@RequestBody Map<String,String> body){

		if(!body.containsKey(POST_MSG_CONTENT_KEY)){
			throw new ApiError(403,"MsgErr","Message content not found");
		}
		CommunityMessage msg = new CommunityMessage();
		msg.setSenderId(operatorId);
		msg.setContent(String.valueOf(body.get(POST_MSG_CONTENT_KEY)));

		return cmSvc.postMessage(uid,msg);
	}

	@PostMapping("/notify/{assoId}")
	public ApiResponse postNotification(
			@RequestAttribute(name="id") Integer operatorId,
			@PathVariable("assoId") String assoId,
			@RequestBody Map<String,String> body){

		if(!body.containsKey(POST_MSG_CONTENT_KEY)){
			throw new ApiError(403,"MsgErr","Message content not found");
		}
		final String assoName = assoSvc.getAssociation(assoId).getData().getName();
		CommunityMessage msg = new CommunityMessage();
		msg.setSenderId(operatorId);
		msg.setSenderAssociation(assoId);
		msg.setSenderName(assoName);
		msg.setContent(String.valueOf(body.get(POST_MSG_CONTENT_KEY)));

		return cmSvc.postNotifications(msg);
	}

	@PostMapping("/msg/read/{mid}")
	public ApiResponse markMessageReadById(
			@RequestAttribute(name="id", required=false) Integer operatorId,
			@PathVariable("mid") int mid){
		return cmSvc.markMessageReadById(operatorId,mid);
	}

	@PostMapping("/msg/readall")
	public ApiResponse markAllMessageRead(
			@RequestAttribute(name="id", required=false) Integer operatorId){
		return cmSvc.markAllMessageRead(operatorId);
	}

	@PostMapping("/msg/drop/{mid}")
	public ApiResponse dropMessageById(
			@RequestAttribute(name="id", required=false) Integer operatorId,
			@PathVariable("mid") int mid){
		return cmSvc.dropMessageById(operatorId,mid);
	}

	@PostMapping("/msg/dropall")
	public ApiResponse dropAllMessage(
			@RequestAttribute(name="id", required=false) Integer operatorId){
		return cmSvc.dropAllMessage(operatorId);
	}

	@GetMapping("/msg")
	public ApiResponse getMessage(
			@RequestAttribute(name="id", required=false) Integer operatorId){
		return cmSvc.getMessageForUser(operatorId);
	}

	@GetMapping("/msg/page")
	public ApiResponse getPagedMessage(
			@RequestAttribute(name="id", required=false) Integer operatorId,
			PageQuery pq){
		return cmSvc.getPagedMessageForUser(operatorId,pq);
	}

	@PostMapping("/sub")
	public ApiResponse commitSubscription(
			@RequestAttribute(name="id", required=false) Integer operatorId,
			@RequestBody List<String> assoIds){
		return cmSvc.addToSubscribed(operatorId,assoIds);
	}

	@DeleteMapping("/sub")
	public ApiResponse removeSubscription(
			@RequestAttribute(name="id", required=false) Integer operatorId,
			@RequestBody List<String> assoIds){
		return cmSvc.removeFromSubscribed(operatorId,assoIds);
	}

	@GetMapping("/sub")
	public ApiResponse listSubscribed(
			@RequestAttribute(name="id", required=false) Integer operatorId){
		return cmSvc.listSubscribed(operatorId);
	}

}
