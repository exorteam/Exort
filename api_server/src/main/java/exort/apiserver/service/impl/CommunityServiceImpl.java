package exort.apiserver.service.impl;

import java.util.List;

import com.google.common.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.apiserver.service.CommunityService;

@Service
public class CommunityServiceImpl extends RestTemplate implements CommunityService {

	@Value("${exort.auth.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.auth.endpoint:localhost}")
    public void setEndpoint(String endpoint) {super.setEndpoint(endpoint);}

	public ApiResponse postNotifications(CommunityMessage msg) {
		final String assoId = msg.getSenderAssociation();
		return request(new TypeToken<Integer>(){},
				msg,
				HttpMethod.POST,
				"/com/notifiy/{assoId}",
				assoId);
	}

	public ApiResponse postMessage(int uid,CommunityMessage msg){
		return request(new TypeToken<Integer>(){},
				msg,
				HttpMethod.POST,
				"/com/msg/{uid}",
				uid);
	}

	public ApiResponse markMessageReadById(int uid,int mid){
		return request(new TypeToken<Integer>(){},
				HttpMethod.POST,
				"/com/msg/read/{uid}/{mid}",
				uid,mid);
	}

	public ApiResponse markAllMessageRead(int uid){
		return request(new TypeToken<Integer>(){},
				HttpMethod.POST,
				"/com/msg/readall/{uid}",
				uid);
	}

	public ApiResponse dropMessageById(int uid,int mid){
		return request(new TypeToken<Integer>(){},
				HttpMethod.POST,
				"/com/msg/drop/{uid}/{mid}",
				uid,mid);
	}

	public ApiResponse dropAllMessage(int uid){
		return request(new TypeToken<Integer>(){},
				HttpMethod.POST,
				"/com/msg/dropall/{uid}",
				uid);
	}

	public ApiResponse getMessageForUser(int uid){
		return request(new TypeToken<List<CommunityMessage>>(){},
				HttpMethod.GET,
				"/com/msg/{uid}",
				uid);
	}

	public ApiResponse getPagedMessageForUser(int uid,PageQuery pq){
		final int pn = pq.getPageNum();
		final int ps = pq.getPageSize();
		return request(new TypeToken<PagedData<CommunityMessage>>(){},
				HttpMethod.GET,
				"/com/msg/page/{uid}?pageNum={pn}&pageSize={ps}",
				uid,pn,ps);
	}

	public ApiResponse addToSubscribed(int uid,List<String> assoIds){
		return request(new TypeToken<List<String>>(){},
				assoIds,
				HttpMethod.POST,
				"/com/sub/{uid}",
				uid);
	}

	public ApiResponse removeFromSubscribed(int uid,List<String> assoIds){
		return request(new TypeToken<List<String>>(){},
				assoIds,
				HttpMethod.DELETE,
				"/com/sub/{uid}",
				uid);
	}

	public ApiResponse listSubscribed(int uid){
		return request(new TypeToken<List<String>>(){},
				HttpMethod.GET,
				"/com/sub/{uid}",
				uid);
	}

}
