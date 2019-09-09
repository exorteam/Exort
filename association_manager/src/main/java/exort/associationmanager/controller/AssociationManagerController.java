package exort.associationmanager.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import exort.api.http.assomgr.entity.Association;
import exort.api.http.assomgr.entity.AssociationFilterParams;
import exort.api.http.assomgr.entity.AssociationInfo;
import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.Operation;
import exort.api.http.common.entity.PageQuery;
import exort.api.http.common.entity.PagedData;
import exort.api.http.common.errorhandler.ApiError;
import exort.associationmanager.service.AssociationService;

@RestController
public class AssociationManagerController{

    @Autowired
    private AssociationService service;

    @GetMapping("/associations")
    public ApiResponse<PagedData<Association>> listAssociations(
			@RequestBody AssociationFilterParams body,
			PageQuery page){

		return new ApiResponse(service.listAssociations(body,page.getPageNum(),page.getPageSize()));
        //if(body.getState() > 2 ||body.getState()<-1){
        //    throw new ApiError(400,"invalidState","无效的状态");
        //}
        //if(false){
        //    throw new ApiError(400,"invalidTags","无效的标签");
        //}
        //if(body.getKeyword() == null){
        //    body.setKeyword("");
        //}
        //if(body.getState() == null){
        //    body.setState(2);
        //}
        //if(body.getTags() == null){
        //    body.setTags(new LinkedList<>());
        //}
        //page = PageQuery.relocate(page, 6, 500);


        //PagedData<exort.associationmanager.entity.Association> associationList = service.listAssociations(body,page.getPageNum(),page.getPageSize());
        //PagedData<Association> associationPagedData = new PagedData<>();
        //associationPagedData.setPageSize(associationList.getPageSize());
        //associationPagedData.setTotalSize(associationList.getTotalSize());
        //associationPagedData.setPageNum(associationList.getPageNum());
        //List<Association> associations = new LinkedList<>();
        //List<exort.associationmanager.entity.Association> oriAssociations = associationList.getContent();
        //for (int i = 0; i <associationList.getContent().size() ; i++) {
        //    associations.add(oriAssociations.get(i).toCommon());
        //}
        //associationPagedData.setContent(associations);

        //return new ApiResponse<>(associationPagedData);
		//
    }
    @GetMapping("/associations/{assoId}")
    public ApiResponse<Association> getAssociation(
			@PathVariable(value="assoId") String assoId){
        if (assoId == null){
            throw new ApiError(400,"invalidAssoId","无效的社团ID");
        }
        exort.associationmanager.entity.Association association = service.getAssociation(assoId);
        Association association1 = association.toCommon();
        if (association == null){
            throw new ApiError(404,"notFound","社团不存在");
        }
        return new ApiResponse<>(association1);
    }
    @PostMapping("/associations")
    public ApiResponse<Association> createAssociation(
			@RequestBody AssociationInfo body){
        String description = body.getDescription() == null ? "" : body.getDescription();
        String logo = body.getLogo() == null ? "" : body.getLogo();
        String name = body.getName() == null ? "请设置名字" : body.getName();
        if(body.getTags() == null){
            body.setTags(new LinkedList<>());
        }
        exort.associationmanager.entity.Association association= service.createAssociation(name,description,body.getTags(),logo);
        Association association1 = association.toCommon();
        if(association == null){
            throw new ApiError(500,"unfoundBug","未发现的Bug，请由提交给Exort");
        }
        return  new ApiResponse(association);
    }

    @DeleteMapping("/associations/{assoId}")
    public ApiResponse<Object> deleteAssociation(
			@PathVariable(value="assoId") String assoId ){
        if (assoId == null){
            throw new ApiError(400,"invalidAssoId","无效的社团ID");
        }
        if(!service.deleteAssociation(assoId)){
            throw new ApiError(400,"notFound","社团不存在");
        }
        return  new ApiResponse(new HashMap<>());

    }
    @PutMapping("/associations/{assoId}")
    public ApiResponse<Association> editAssociation(
			@RequestBody AssociationInfo body,
			@PathVariable(value="assoId") String assoId ){
        String description = body.getDescription() == null ? "" : body.getDescription();
        String logo = body.getLogo() == null ? "" : body.getLogo();
        String name = body.getName() == null ? "请设置名字" : body.getName();
        if(body.getTags() == null){
            body.setTags(new LinkedList<>());
        }
        exort.associationmanager.entity.Association association = service.editAssociation(assoId, name, description,body.getTags(),logo);
        Association association1 = association.toCommon();
        if(association == null){
            throw new ApiError(500,"unfoundBug","未发现的Bug，请由提交给Exort");
        }
        return new ApiResponse(association);
    }


    @PutMapping("/associations/{assoId}/state")
    public ApiResponse<Object> patchAssociation(
			@RequestBody Operation<String> body,
			@PathVariable(value="assoId") String assoId ){

        if(body.getArg()==null){
            body.setArg("");
        }

        if(body.getOperation() == null | !Arrays.asList("block","unblock").contains(body.getOperation() )){
            throw new ApiError(400,"invlaidType","无效的申请类型");
        }
        if(!service.patchAssociation(assoId,body.getOperation(),body.getArg())){
            throw new ApiError(500,"unfoundBug","未发现的Bug，请由提交给Exort");
        }
        return new ApiResponse(new HashMap<>());
    }

	@PostMapping("/associations/batch")
	public ApiResponse<PagedData<Association>> getAssociationsInBatch(
			@RequestBody List<String> ids,
			PageQuery pq){
		if(ids.isEmpty()){
			throw new ApiError(400,"emptyList","Empty id list.");
		}
		return new ApiResponse(service.getAssociationsInBatch(ids,pq.getPageNum(),pq.getPageSize()));
	}
}
