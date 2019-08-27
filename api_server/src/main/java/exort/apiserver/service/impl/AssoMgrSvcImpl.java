package exort.apiserver.service.impl;

import com.google.common.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import exort.api.http.assomgr.entity.Association;
import exort.api.http.common.entity.Operation;
import exort.api.http.assomgr.entity.AssociationFilterParams;
import exort.api.http.common.RestTemplate;
import exort.api.http.assomgr.entity.AssociationInfo;
import exort.api.http.assomgr.service.AssociationManagerService;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;

@Service
public class AssoMgrSvcImpl extends RestTemplate implements AssociationManagerService {

	@Value("${exort.assomgr.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.assomgr.endpoint:localhost}")
    public void setLocalhost(String endpoint) { super.setEndpoint(endpoint); }

    @Override
    public ApiResponse<Association> createAssociation(AssociationInfo body){
        return request(new TypeToken<Association>() {}, body,
                HttpMethod.POST, "/associations");
    }

    @Override
    public ApiResponse<PagedData<Association>> listAssociations(AssociationFilterParams body, PageQuery pageQuery){
        return request(new TypeToken<PagedData<Association>>() {}, body,
                HttpMethod.GET, pageQuery, "/associations");
    }
    @Override
    public ApiResponse<Association> getAssociation(String assoId){
        return request(new TypeToken<Association>() {},
                HttpMethod.GET, "/associations/{assoId}", assoId);
    }
    @Override
    public ApiResponse<Object> deleteAssociation(String assoId ){
        return request(new TypeToken<Object>() {},
                HttpMethod.DELETE, "/associations/{assoId}", assoId);
    }
    @Override
    public ApiResponse<Association> editAssociation(String assoId, AssociationInfo body){
        return request(new TypeToken<Association>() {}, body,
                HttpMethod.PUT, "/associations/{assoId}", assoId);
    }
    @Override
    public ApiResponse<Object> patchAssociation(String assoId, Operation<String> body){
        return request(new TypeToken<Object>() {}, body,
                HttpMethod.PUT, "/associations/{assoId}/state", assoId);

    }
}
