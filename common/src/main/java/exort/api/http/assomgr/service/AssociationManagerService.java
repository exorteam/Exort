package exort.api.http.assomgr.service;

import exort.api.http.assomgr.entity.*;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.Operation;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.review.entity.CallbackParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Service;
import java.util.List;

@Service

public interface AssociationManagerService {

    ApiResponse<Association> getAssociation(String assoId);

    ApiResponse<PagedData<Association>> listAssociations(AssociationFilterParams body, PageQuery pageQuery);

    ApiResponse<Association> createAssociation(AssociationInfo body);

    ApiResponse<Object> deleteAssociation(String assoId );

    ApiResponse<Association> editAssociation(String assoId, AssociationInfo body);

    ApiResponse<Object> patchAssociation(String assoId, Operation<String> body);

//    ApiResponse<Object> handleCreateAsoociationApplication(CallbackParam<AssociationInfo> body);
//
//    ApiResponse<Object> handleUnblockAsoociationApplication(CallbackParam<Operation<String>> body);

}
