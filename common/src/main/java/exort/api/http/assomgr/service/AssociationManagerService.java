package exort.api.http.assomgr.service;

import exort.api.http.assomgr.entity.*;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.Operation;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import org.springframework.stereotype.Service;

@Service

public interface AssociationManagerService {

    ApiResponse<Association> getAssociation(String assoId);

    ApiResponse<PagedData<Association>> listAssociations(AssociationFilterParams body, PageQuery pageQuery);

    ApiResponse<Association> createAssociation(AssociationInfo body);

    ApiResponse<Object> deleteAssociation(String assoId );

    ApiResponse<Association> editAssociation(String assoId, AssociationInfo body);

    ApiResponse<Object> patchAssociation(String assoId, Operation<String> body);

}
