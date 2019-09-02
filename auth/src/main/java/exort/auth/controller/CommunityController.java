package exort.auth.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.auth.entity.CommunityMessage;
import exort.auth.service.CommunityService;

@RestController
@RequestMapping(path="/com")
public class CommunityController {

	@Autowired
	private CommunityService cmSvc;

	@PostMapping("/msg/{uid}")
	public ApiResponse<Integer> postMessage(
			@PathVariable("uid") Integer uid,
			@RequestBody CommunityMessage msg){
		if(msg.getSenderId() == null){
			throw new ApiError(403,"MsgErr","Message attribute senderId is null");
		}
		if(msg.getContent() == null || msg.getContent().isEmpty()){
			throw new ApiError(403,"MsgErr","Message content is null or empty");
		}

		final Integer res = cmSvc.postMessage(uid,msg);
		if(res == null){
			throw new ApiError(403,"QueryErr","Cannot found such user");
		}

		return new ApiResponse<Integer>(res);
	}

	@PostMapping("/msg/read/{uid}/{mid}")
	public ApiResponse<Integer> markMessageAsReadById(
			@PathVariable("uid") Integer uid,
			@PathVariable("mid") Integer mid){
		final Integer res = cmSvc.markMessageAsReadById(uid,mid);
		if(res == null){
			throw new ApiError(403,"QueryErr","Cannot found such user or msg");
		}

		return new ApiResponse(res);
	}

	@PostMapping("/msg/readall/{uid}")
	public ApiResponse<Integer> markAllMessageAsRead(
			@PathVariable("uid") Integer uid){
		final Integer res = cmSvc.markAllMessageAsRead(uid);
		if(res == null){
			throw new ApiError(403,"QueryErr","Cannot found such user");
		}

		return new ApiResponse(res);
	}

	@PostMapping("/msg/drop/{uid}/{mid}")
	public ApiResponse<Integer> dropMessageById(
			@PathVariable("uid") Integer uid,
			@PathVariable("mid") Integer mid){
		final Integer res = cmSvc.dropMessageById(uid,mid);
		if(res == null){
			throw new ApiError(403,"QueryErr","Cannot found such user");
		}

		return new ApiResponse(res);
	}

	@PostMapping("/msg/dropall/{uid}")
	public ApiResponse<Integer> dropAllMessage(
			@PathVariable("uid") Integer uid){
		final Integer res = cmSvc.dropAllMessage(uid);
		if(res == null){
			throw new ApiError(403,"QueryErr","Cannot found such user");
		}

		return new ApiResponse(res);
	}

	@GetMapping("/msg/{uid}")
	public ApiResponse<List<CommunityMessage>> getMessagesForUser(
			@PathVariable("uid") Integer uid){
		final List<CommunityMessage> res = cmSvc.getMessagesForUser(uid);
		if(res == null){
			throw new ApiError(403,"QueryErr","Cannot found such user");
		}

		return new ApiResponse(res);
	}

	@PostMapping("/sub/{uid}")
	public ApiResponse<List<String>> addToSubscribed(
			@PathVariable("uid") Integer uid,
			@RequestBody List<String> assoIds){
		final List<String> res = cmSvc.addToSubscribed(uid,assoIds);
		if(res == null){
			throw new ApiError(403,"QueryErr","Cannot found such user");
		}

		return new ApiResponse(res);
	}

	@DeleteMapping("/sub/{uid}")
	public ApiResponse<List<String>> removeFromSubscribed(
			@PathVariable("uid") Integer uid,
			@RequestBody List<String> assoIds){
		final List<String> res = cmSvc.removeFromSubscribed(uid,assoIds);
		if(res == null){
			throw new ApiError(403,"QueryErr","Cannot found such user");
		}

		return new ApiResponse(res);
	}

	@GetMapping("/sub/{uid}")
	public ApiResponse<List<String>> listSubscribed(
			@PathVariable("uid") Integer uid){
		final List<String> res = cmSvc.listSubscribed(uid);
		if(res == null){
			throw new ApiError(403,"QueryErr","Cannot found such user");
		}

		return new ApiResponse(res);
	}

}
