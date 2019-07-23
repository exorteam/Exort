package exort.apiserver.service.impl;

import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import exort.apiserver.service.ActivityManagerService;

@Service
public class ActivityManagerServiceImpl implements ActivityManagerService {

    private RestTemplate rt = new RestTemplate();

    public Response createNewActivity(Activity activity){
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Activity> requestEntity = new HttpEntity<>(activity, headers);
        ResponseEntity<Response> response = rt.exchange("http://202.120.40.8:30727/activities",method,requestEntity, Response.class);
        return response.getBody();
    }

    public Response updateActivity(Activity activity, String activityid){
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.PUT;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Activity> requestEntity = new HttpEntity<>(activity, headers);
        ResponseEntity<Response> response = rt.exchange("http://202.120.40.8:30727/activities/"+ activityid, method,requestEntity, Response.class);
        return response.getBody();
    }

    public Response getActivities(Select select, int pagesize, int pagenum, int sortby){
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_JSON);
           HttpEntity<Select> requestEntity = new HttpEntity<>(select, headers);
        ResponseEntity<Response> response = rt.exchange("http://202.120.40.8:30727/activities"+"?pagesize="+Integer.toString(pagesize) +"&pagenum="+Integer.toString(pagenum)+"&sortby="+Integer.toString(sortby), method, requestEntity, Response.class);
        return response.getBody();
    }

    public Response publishActivity(String activityid, String type){
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.PATCH;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(type, headers);
        ResponseEntity<Response> response = rt.exchange("http://202.120.40.8:30727/activities/"+ activityid,method,requestEntity, Response.class);
        return response.getBody();
    }

    public Response addParticipants(String activityid, List<Integer> participantIds){
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(participantIds, headers);
        ResponseEntity<Response> response = rt.exchange("http://202.120.40.8:30727/activities/"+activityid+"/participants",method,requestEntity, Response.class);
        return response.getBody();
    }

    public Response addRealParticipants(String activityid, List<Integer> realParticipantIds){
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(realParticipantIds, headers);
        ResponseEntity<Response> response = rt.exchange("http://202.120.40.8:30727/activities/"+activityid+"/realparticipants",method,requestEntity, Response.class);
        return response.getBody();
    }

    public Response deleteParticipants(String activityid, List<Integer> participantIds){
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.DELETE;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(participantIds, headers);
        ResponseEntity<Response> response = rt.exchange("http://202.120.40.8:30727/activities/"+activityid+"/participants",method,requestEntity, Response.class);
        return response.getBody();
    }

    public Response getActivityParticipants(int pagesize, int pagenum, String activityid, List<Integer> participantIds){
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(participantIds, headers);
        ResponseEntity<Response> response = rt.exchange("http://202.120.40.8:30727/activities/"+activityid+"/participants"+"?pagesize="+Integer.toString(pagesize) +"&pagenum="+Integer.toString(pagenum), method,requestEntity,Response.class);
        return response.getBody();
    }

    public Response getActivityRealParticipants(int pagesize, int pagenum, String activityid, List<Integer> realParticipantIds){
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_JSON);
           HttpEntity<List<Integer>> requestEntity = new HttpEntity<>(realParticipantIds, headers);
        ResponseEntity<Response> response = rt.exchange("http://202.120.40.8:30727/activities/"+activityid+"/realparticipants"+"?pagesize="+Integer.toString(pagesize) +"&pagenum="+Integer.toString(pagenum),method,requestEntity, Response.class);
        return response.getBody();
    }

    public Response acceptSignup(Operation operation){
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.POST;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Operation> requestEntity = new HttpEntity<>(operation,headers);
        ResponseEntity<Response> response = rt.exchange("http://202.120.40.8:30727/callback/acceptsignup",method,requestEntity, Response.class);
        return response.getBody();
    }

    public Response getActivity(String activityid){
        HttpHeaders headers = new HttpHeaders();
        HttpMethod method = HttpMethod.GET;
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Response> response = rt.exchange("http://202.120.40.8:30727/activities"+activityid,method,requestEntity, Response.class);
        return response.getBody();
    }
}
