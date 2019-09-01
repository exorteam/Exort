package exort.apiserver.service;

import java.util.List;

import exort.api.http.common.entity.Operation;
import exort.api.http.assomgr.entity.Association;
import exort.api.http.assomgr.entity.AssociationFilterParams;
import exort.api.http.assomgr.entity.AssociationInfo;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;

public interface AssoMgrSvc {

	ApiResponse<Association> getAssociation(String assoId);
	ApiResponse<PagedData<Association>> getAssociationsInBatch(List<String> ids, Integer pn, Integer ps);

    ApiResponse<PagedData<Association>> listAssociations(AssociationFilterParams body, PageQuery pageQuery);

    ApiResponse<Association> createAssociation(AssociationInfo body);

    ApiResponse<Object> deleteAssociation(String assoId );

    ApiResponse<Association> editAssociation(String assoId, AssociationInfo body);

    ApiResponse<Object> patchAssociation(String assoId, Operation<String> body);

}
