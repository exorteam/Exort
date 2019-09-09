package exort.auth.service;

import java.util.List;

import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.auth.entity.CommunityMessage;
import exort.auth.entity.UserCommunityEntity;

public interface CommunityService {

	UserCommunityEntity createUser(int uid);
	UserCommunityEntity updateUser(UserCommunityEntity e);

	Integer postNotifications(CommunityMessage msg);
	Integer postSysNotifications(CommunityMessage msg);
	Integer postMessage(int uid,CommunityMessage msg);
	Integer markMessageAsReadById(int uid,int msgId);
	Integer markAllMessageAsRead(int uid);
	Integer dropMessageById(int uid,int msgId);
	Integer dropAllMessage(int uid);
	List<CommunityMessage> getMessagesForUser(int uid);
	PagedData<CommunityMessage> getPagedMessagesForUser(int uid,PageQuery pq);

	List<String> addToSubscribed(int uid,List<String> assoIds);
	List<String> removeFromSubscribed(int uid,List<String> assoIds);
	List<String> listSubscribed(int uid);

}
