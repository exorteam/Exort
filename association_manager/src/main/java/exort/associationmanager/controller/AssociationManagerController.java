package exort.associationmanager.controller;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.errorhandler.ApiError;
import exort.associationmanager.entity.*;
import exort.associationmanager.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

@RestController
public class AssociationManagerController{

    @Autowired
    private AssociationService service;

//    private boolean checkAssociationInfo(){
//
//    }

    @GetMapping("/associations")
    public ApiResponse<AssociationList> listAssociations(@RequestBody AssociationFilterParams body, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize ){
//        System.out.println(body.toString());
        if(body.getState() > 2 ||body.getState()<-1){
            throw new ApiError(400,"invalidState","无效的状态");
        }
        if(false){
            throw new ApiError(400,"invalidTags","无效的标签");
        }
        if(body.getKeyword() == null){
            body.setKeyword("");
        }
        if(body.getState() == null){
            body.setState(2);
        }
        if(body.getTags() == null){
            body.setTags(new LinkedList<>());
        }
        AssociationList associationList = service.listAssociations(body,pageNum,pageSize);
        return new ApiResponse<>(associationList);
    }
    @GetMapping("/associations/{assoId}")
    public ApiResponse<Association> getAssociation(@PathVariable(value="assoId") String assoId){
        if (assoId == null){
            throw new ApiError(400,"invalidAssoId","无效的社团ID");
        }
        Association association = service.getAssociation(assoId);
        if (association == null){
            throw  new  ApiError(404,"notFound","社团不存在");
        }
        return new ApiResponse<>(association);
    }
    @PostMapping("/associations")
    public ApiResponse<Association> createAssociation(@RequestBody AssociationInfo body){
        if(body.getDescription() == null || body.getLogo() == null || body.getName() == null){
            throw new ApiError(400,"invalidFormat","无效的申请格式");
        }
        if(body.getTags() == null){
            body.setTags(new LinkedList<>());
        }
        Association association = service.createAssociation(body.getName(),body.getDescription(),body.getTags(),body.getLogo());
        if(association == null){
            throw new ApiError(400,"unfoundBug","未发现的Bug，请由提交给Exort");
        }
        return  new ApiResponse(association);
    }

    @DeleteMapping("/associations/{assoId}")
    public ApiResponse<Object> deleteAssociation(@PathVariable(value="assoId") String assoId ){
        if (assoId == null){
            throw new ApiError(400,"invalidAssoId","无效的社团ID");
        }
        if(!service.deleteAssociation(assoId)){
            throw new ApiError(400,"notFound","社团不存在");
        }
        return  new ApiResponse(new HashMap<>());

    }
    @PutMapping("/associations/{assoId}")
    public ApiResponse<Association> editAssociation(@RequestBody AssociationInfo body, @PathVariable(value="assoId") String assoId ){
        if(body.getDescription() == null || body.getLogo() == null || body.getName() == null){
            throw new ApiError(400,"invalidFormat","无效的申请格式");
        }
        if(body.getTags() == null){
            body.setTags(new LinkedList<>());
        }
        Association association = service.editAssociation(assoId, body.getName(),body.getDescription(),body.getTags(),body.getLogo());
        if(association == null){
            throw new ApiError(400,"unfoundBug","未发现的Bug，请由提交给Exort");
        }
        return new ApiResponse(association);
    }


    @PutMapping("/associations/{assoId}/state")
    public ApiResponse<Object> patchAssociation(@RequestBody PatchAssociationInfo body, @PathVariable(value="assoId") String assoId ){
        if(body.getType() == null || body.getType()!="block" || body.getType() != "unblock"){
            throw new ApiError(400,"invlaidType","无效的申请类型");
        }
        if(!service.patchAssociation(assoId,body.getType(),body.getDescription())){
            throw new ApiError(400,"unfoundBug","未发现的Bug，请由提交给Exort");
        }
        return new ApiResponse(new HashMap<>());
    }

    @PostMapping("/callback")
    public ApiResponse<Object> handleAssociationApplication(@RequestBody ApplicationAssociationInfo body){
        if( !(Arrays.asList("refuse", "accept", "cancel","create").contains (body.getEvent()))){
            throw new ApiError(400,"invalidEvent","无效的申请类型");
        }
        if(!service.handleAsoociationApplication(body.getUserId(),body.getEvent(),body.getApplication())){
            throw new ApiError(400,"unfoundBug","未发现的Bug，请由提交给Exort");
        }
        return new ApiResponse(new HashMap<>());
    }
}
