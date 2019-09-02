package exort.apiserver.service;

import java.util.Date;
import java.util.List;

import exort.api.http.common.entity.ApiResponse;
import lombok.Data;

public interface CommunityService {

	@Data
	public class CoummunityMessage {

		private int id;
		private int senderId;
		private String content;
		private Date timestamp;
		private boolean read;

	}

	ApiResponse postMessage(int uid,CoummunityMessage msg);
	ApiResponse markMessageReadById(int uid,int mid);
	ApiResponse markAllMessageRead(int uid);
	ApiResponse dropMessageById(int uid,int mid);
	ApiResponse dropAllMessage(int uid);
	ApiResponse getMessageForUser(int uid);

	ApiResponse addToSubscribed(int uid,List<String> assoIds);
	ApiResponse removeFromSubscribed(int uid,List<String> assoIds);
	ApiResponse listSubscribed(int uid);

}

