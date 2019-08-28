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
import exort.api.http.assomgr.service.AssociationManagerService;
import exort.api.http.common.RestTemplate;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.Operation;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.apiserver.component.StaticFileUtil;

@Service
public class AssoMgrSvcImpl extends RestTemplate implements AssociationManagerService {

	@Autowired
	private StaticFileUtil sfu;

	@Value("${exort.assomgr.protocol:http}")
    public void setProtocol(String protocol) { super.setProtocol(protocol); }

    @Value("${exort.assomgr.endpoint:localhost}")
    public void setLocalhost(String endpoint) { super.setEndpoint(endpoint); }

    @Override
    public ApiResponse<Association> createAssociation(AssociationInfo body){
		final String  imgname = sfu.storeFile(body.getLogo().getBytes());
		body.setLogo(imgname);

        return request(new TypeToken<Association>() {}, body,
                HttpMethod.POST, "/associations");
    }

    @Override
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
		final String imgname = sfu.storeFile(body.getLogo().getBytes());
		body.setLogo(imgname);

        return request(new TypeToken<Association>() {}, body,
                HttpMethod.PUT, "/associations/{assoId}", assoId);
    }
    @Override
    public ApiResponse<Object> patchAssociation(String assoId, Operation<String> body){
        return request(new TypeToken<Object>() {}, body,
                HttpMethod.PUT, "/associations/{assoId}/state", assoId);

    }
}
