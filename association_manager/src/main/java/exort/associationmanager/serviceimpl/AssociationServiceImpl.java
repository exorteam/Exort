package exort.associationmanager.serviceimpl;

import java.util.*;

import exort.api.http.common.entity.ApiResponse;
import exort.api.http.common.entity.PagedData;
import exort.api.http.common.errorhandler.ApiError;
import exort.api.http.perm.entity.Permission;
import exort.api.http.perm.service.PermService;
import exort.api.http.review.entity.Application;
import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.entity.MyObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import exort.associationmanager.repository.AssociationRepository;
import exort.associationmanager.service.AssociationService;
import org.springframework.stereotype.Service;

import static java.lang.Math.min;

@Service
public class AssociationServiceImpl implements AssociationService{
    @Autowired
    private PermService permService;

    @Autowired
    private AssociationRepository assoRepository;
    public Association getAssociation(String assoId){
        Association association = new Association();
        if (assoRepository.existsById(assoId)) {
            association = assoRepository.findById(assoId).get();
        }
        if (association == new Association() ) return null;
        return  association;
    }
    public Association createAssociation(String name,String description,List<String> tags,String logo){
        Association association= new Association(new ObjectId().toString(),name,description,logo,tags,1,null);
        assoRepository.save(association);
        return association;
    }
    public Association createAssociationWithId(String assoId, String name,String description,List<String> tags,String logo){
        Association association= new Association(assoId,name,description,logo,tags,1,null);
        assoRepository.save(association);
        return association;
    }
    public PagedData<Association> listAssociations(AssociationFilterParams params, Integer pageNum, Integer pageSize){
        List<Association> associations = assoRepository.findAll();
        Integer state = params.getState();
        if(state != null && state != 2){
            associations.removeIf(association -> !state.equals(association.getState()));
            if(associations.isEmpty()){
                return new PagedData<>(0,0,Long.valueOf(0),associations);
            }
        }
        String keyword = params.getKeyword();
        if(keyword != null && keyword!=""){
            int size = associations.size();
            if (keyword != null) {
                for (int i = size - 1; i >= 0; i--) {
                    if (!associations.get(i).hasKeyword(keyword)) {
                        associations.remove(i);
                    }
                }
                if (associations.isEmpty()) {
                    return new PagedData<>(0,0,Long.valueOf(0),associations);
                }
            }
        }
        System.out.println(associations.size());
        List<String> tags = params.getTags();
        if(!(tags == null || (tags.size()==1&&tags.get(0)==""))) {
            int size = associations.size();
            if (tags != null) {
                for (int i = size - 1; i >= 0; i--) {
                    if (!associations.get(i).hasTags(tags)) {
                        associations.remove(i);
                    }
                }
                if (associations.isEmpty()) {
                    return new PagedData<>(0,0,Long.valueOf(0),associations);
                }
            }
        }
        System.out.println(associations.size());
        int totalSize = associations.size();

        int max =20;
        int realPageSize,realPageNum;

        realPageSize = min(pageSize,max);
        realPageNum = pageNum * pageSize / realPageSize;
        int subFirst = realPageNum * realPageSize;
        int subLast = min((realPageNum + 1) * realPageSize,totalSize);
        if(subFirst >= totalSize){
            subFirst = 0;
            subLast = 0;
        }
        return new PagedData<Association>(realPageNum,realPageSize,Long.valueOf(totalSize),associations.subList(subFirst,subLast));
    }

    public boolean deleteAssociation(String assoId ){
        if(assoRepository.findById(assoId)==null){
            return false;
        }
        assoRepository.deleteById(assoId);
        return true;
    }
    public Association editAssociation(String assoId, String name, String description, List<String> tags, String logo){
        if(assoRepository.findById(assoId)==null){
            return null;
        }
        Association association = assoRepository.findById(assoId).get();
        association.setDescription(description);
        association.setTags(tags);
        association.setName(name);
        association.setLogo(logo);
        assoRepository.save(association);
        return association;
    }

    public  boolean patchAssociation(String assoId,String type,String reason){
        Association association = assoRepository.findById(assoId).get();
        if(assoRepository.findById(assoId)==null){
            return  false;
        }
        switch (type){
            case "unblock":
                association.setState(1);
                association.setReason("");
                assoRepository.save(association);
                break;
            case "block":
                association.setState(0);
                association.setReason(reason);
                assoRepository.save(association);
        }
        return true;
    }

    private boolean hasAuth(Long user_id,String scope,String roleName){
//        ApiResponse<Permission> response = permService.hasPermission(user_id,scope,permission);
        ApiResponse<Permission> response = permService.hasRole(user_id,scope,roleName);
        if (response.getData() == null) {
            return false;
        } else {
            return true;
        }
    };

    public boolean handleAsoociationApplication(Long user_id, String type, Application<MyObject> app ){
        switch (type) {
            case "accept": //create association
                switch (app.getType()) {
                    case "createAssociation":
                        if (!hasAuth(user_id,"system","SysManager")) {    //有权限，待修改
                            throw new ApiError(400, "noAuthorized", "用户未提供身份验证凭据，或者没有通过身份验证");
                        }
                        MyObject assoInfo = app.getObject();
                        createAssociation(assoInfo.getName(),assoInfo.getDescription(),assoInfo.getTags(),assoInfo.getLogo());
                        return true;
                    case "unblockAssociation":
                        if (hasAuth(user_id,"system","SysManager")) {
                            throw new ApiError(400, "noAuthorized", "用户未提供身份验证凭据，或者没有通过身份验证");
                        }
                        MyObject blockInfo = app.getObject();
                        Association asso = assoRepository.findById(blockInfo.getAssociationId()).get();
                        asso.setState(1);
                        assoRepository.save(asso);
                        return true;
                }
            case "refuse":
                switch (app.getType()) {
                    case "createAssociation":
                        if (!hasAuth(user_id,"system","SysManager")) {    //有权限，待修改
                            throw new ApiError(400, "noAuthorized", "用户未提供身份验证凭据，或者没有通过身份验证");
                        }
                        return true;
                    case "unblockAssociation":
                        if (hasAuth(user_id,"system","SysManager")) {
                            throw new ApiError(400, "noAuthorized", "用户未提供身份验证凭据，或者没有通过身份验证");
                        }
                        return true;
                }
            case "cancel":
                switch (app.getType()) {
                    case "createAssociation":
                        if (!hasAuth(user_id,"system","AssoManager")) {    //有权限，待修改
                            throw new ApiError(400, "noAuthorized", "用户未提供身份验证凭据，或者没有通过身份验证");
                        }
                        return true;
                    case "unblockAssociation":
                        if (hasAuth(user_id,"system","AsooManager")) {
                            throw new ApiError(400, "noAuthorized", "用户未提供身份验证凭据，或者没有通过身份验证");
                        }
                        return true;
                }
            default:
                throw new ApiError(400,"invalidType","无效的申请类型");
        }
    };
    public  boolean createTestData(){

        for (int i = 0; i <10000 ; i++) {
            Association association = generateAsso();
            assoRepository.save(association);
        }
        return  true;

    };
    private  Association generateAsso(){
        Association association = new Association();
        String name = "";
        String description = "";
        Integer state = null;
        String reason = "";
        List<String> chars =  Arrays.asList("a", "b", "c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
        List<String> tags = new LinkedList<>();
        List<Integer> states =  Arrays.asList(0,1);
        int size = (int) (Math.random()*5);
        for(int i=0; i<size; i++){
            int index = (int) (Math.random() * chars.size());
            tags.add(chars.get(index));
        }
        for(int i=0; i<10; i++){
            int index = (int) (Math.random()* chars.size());
            name = name + chars.get(index);
        }
        for(int i=0; i<40; i++){
            int index = (int) (Math.random()* chars.size());
            description = description + chars.get(index);
        }
        for(int i=0; i<10; i++){
            int index = (int) (Math.random()* chars.size());
            reason = reason + chars.get(index);
        }
        int index = (int) (Math.random()* states.size());
        state = states.get(index);
        association.setName(name);
        association.setState(state);
        association.setReason(reason);
        association.setDescription(description);
        association.setLogo("data:image/jpeg;base64,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");
        association.setTags(tags);
        return association;
    }
}
