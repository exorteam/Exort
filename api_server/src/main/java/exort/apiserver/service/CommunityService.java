package exort.apiserver.service;

import java.util.Date;
import java.util.List;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import lombok.Data;

public interface CommunityService {

	@Data
	public class CommunityMessage {

		private int id;
		private int senderId;
		private String senderAssociation;
		private String senderName;

		private String content;
		private Date timestamp;
		private boolean read;

	}

	ApiResponse postNotifications(CommunityMessage msg);
	ApiResponse postSysNotifications(CommunityMessage msg);
	ApiResponse postMessage(int uid,CommunityMessage msg);
	ApiResponse markMessageReadById(int uid,int mid);
	ApiResponse markAllMessageRead(int uid);
	ApiResponse dropMessageById(int uid,int mid);
	ApiResponse dropAllMessage(int uid);
	ApiResponse getMessageForUser(int uid);
	ApiResponse getPagedMessageForUser(int uid,PageQuery pq);


	ApiResponse addToSubscribed(int uid,List<String> assoIds);
	ApiResponse removeFromSubscribed(int uid,List<String> assoIds);
	ApiResponse listSubscribed(int uid);

}

