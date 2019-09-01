package exort.apiserver.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.google.common.reflect.TypeToken;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import exort.api.http.assomgr.entity.Association;
import exort.api.http.assomgr.entity.AssociationFilterParams;
import exort.api.http.assomgr.entity.AssociationInfo;
import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.Operation;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.apiserver.component.StaticFileUtil;
import exort.apiserver.service.AssoMgrSvc;

@Service
public class AssoMgrSvcImpl extends RestTemplate implements AssoMgrSvc {

	@Autowired
	private StaticFileUtil sfu;

	@Value("${exort.assomgr.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.assomgr.endpoint:localhost}")
    public void setLocalhost(String endpoint) { super.setEndpoint(endpoint); }

    public ApiResponse<Association> createAssociation(AssociationInfo body){
		final String  imgname = sfu.storeFile(body.getLogo().getBytes());
		body.setLogo(imgname);

        return request(new TypeToken<Association>() {}, body,
                HttpMethod.POST, "/associations");
    }

	public ApiResponse<PagedData<Association>> getAssociationsInBatch(List<String> ids,Integer pn,Integer ps){
		return request(new TypeToken<PagedData<Association>>(){},
				ids,
				HttpMethod.POST,
				"/associations/batch?pageNum={pn}&pageSize={ps}",
				pn,ps);
	}

    public ApiResponse<PagedData<Association>> listAssociations(AssociationFilterParams body, PageQuery pageQuery){
		final Integer pageNum = pageQuery.getPageNum();
		final Integer pageSize = pageQuery.getPageSize();
        final ApiResponse<PagedData<Association>> res = request(
				new TypeToken<PagedData<Association>>(){}, 
				body, 
				HttpMethod.GET, 
				"/associations?pageNum={pageNum}&pageSize={pageSize}",
				pageNum,pageSize);
		PagedData<Association> p = res.getData();

		List<Association> assos = p.getContent();
		List<Association> nassos = new ArrayList<Association>();
		for(Association asso : assos){
			asso.setLogo(new String(sfu.getFile(asso.getLogo())));
			nassos.add(asso);
		}
		p.setContent(nassos);
		
		return new ApiResponse<PagedData<Association>>(p);
    }

    public ApiResponse<Association> getAssociation(String assoId){
        Association asso = request(new TypeToken<Association>() {},
                HttpMethod.GET, "/associations/{assoId}", assoId).getData();
		asso.setLogo(new String(sfu.getFile(asso.getLogo())));

		return new ApiResponse<Association>(asso);
    }

    public ApiResponse<Object> deleteAssociation(String assoId ){
        return request(new TypeToken<Object>() {},
                HttpMethod.DELETE, "/associations/{assoId}", assoId);
    }

    public ApiResponse<Association> editAssociation(String assoId, AssociationInfo body){
		final String imgname = sfu.storeFile(body.getLogo().getBytes());
		body.setLogo(imgname);

        return request(new TypeToken<Association>() {}, body,
                HttpMethod.PUT, "/associations/{assoId}", assoId);
    }

    public ApiResponse<Object> patchAssociation(String assoId, Operation<String> body){
        return request(new TypeToken<Object>() {}, body,
                HttpMethod.PUT, "/associations/{assoId}/state", assoId);

    }
}
