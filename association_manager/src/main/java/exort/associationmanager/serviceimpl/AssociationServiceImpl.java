package exort.associationmanager.serviceimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import exort.associationmanager.entity.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exort.associationmanager.repository.AssociationRepository;
import exort.associationmanager.service.AssociationService;

@Service
public class AssociationServiceImpl implements AssociationService{
    @Autowired
    private AssociationRepository assoRepository;
//    private AssociationApplicationRepository assoAppRepository;

    public ResponseBody getAssociation(Integer assoId){
        ResponseBody responseBody = new ResponseBody();
        Association association = assoRepository.findById(assoId).get();
        if(association==null){
            responseBody.setData(null);
            responseBody.setError("notFound");
            responseBody.setMessage("不存在该社团");
            return  responseBody;
        }
        responseBody.setData(association);
        responseBody.setMessage("");
        responseBody.setError("");
        return  responseBody;
    }

    public ResponseBody listAssociations(AssociationFilterParams params,Integer pageNum,Integer pageSize){
        ResponseBody responseBody = new ResponseBody();

        List<Association> associations = assoRepository.findAll();

        Integer state = params.getState();

        if(state != null){
            associations.removeIf(association -> !state.equals(association.getState()));
            if(associations.isEmpty()){

                return responseBody.setAndGetResponsebody(associations,"","");
            }
        }

        String keyword = params.getKeyword();
        if(keyword != null){
            associations.removeIf(association -> !association.getName().contains(keyword)||!association.getDescription().contains(keyword));
            if(associations.isEmpty()){
                return responseBody.setAndGetResponsebody(associations,"","");
            }
        }

        List<String> tags = params.getTags();
        if(tags != null){
            for (Integer i = 0; i < associations.size(); i++) {
                if(!associations.get(i).hasTags(tags)){
                    associations.remove(i);
                }
            }
            if(associations.isEmpty()){
                return responseBody.setAndGetResponsebody(associations,"","");
            }
        }
        return responseBody.setAndGetResponsebody(associations,"","");
    }


    public ResponseBody createAssociation(String name,String description,List<String> tags,String logo){
        ResponseBody responseBody = new ResponseBody();

        if( name == null || description == null || tags == null || logo == null){
            return responseBody.setAndGetResponsebody(null,"invalidFormat","无效的申请格式");
        }

        Association association= new Association();

        association.setName(name);
        association.setDescription(description);
        association.setLogo(logo);
        association.setTags(tags);
        association.setState(1);
        association.setReason(null);

        Integer associationId = 1;
        while(assoRepository.existsById(associationId)){++associationId;}
        association.setId(associationId);

        assoRepository.save(association);
        responseBody.setData(association);
        return responseBody.setAndGetResponsebody(association,"","");
    }

    private ResponseBody  noAssoMessage (ResponseBody responseBody){
        return responseBody.setAndGetResponsebody(null,"notFound","社团不存在");
    }

    public ResponseBody deleteAssociation(Integer assoId ){
        ResponseBody responseBody = new ResponseBody();
        if(!assoRepository.existsById(assoId)){
            return  noAssoMessage(responseBody);
        }
        assoRepository.deleteById(assoId);
        responseBody.setData(new HashMap());
        return responseBody.setAndGetResponsebody(new HashMap(),"","");
    }
    public ResponseBody editAssociation(Integer assoId, String name, String description, List<String> tags, String logo){
        ResponseBody responseBody = new ResponseBody();
        if(!assoRepository.existsById(assoId)){
            return  noAssoMessage(responseBody);
        }
        Association association = assoRepository.findById(assoId).get();
        association.setDescription(description);
        association.setTags(tags);
        association.setName(name);
        association.setLogo(logo);
        assoRepository.save(association);
        return responseBody.setAndGetResponsebody(association,"","");
    };

    public  ResponseBody patchAssociation(Integer assoId,String type,String reason){
        ResponseBody responseBody = new ResponseBody();
        Association association = assoRepository.findById(assoId).get();
        if(!assoRepository.existsById(assoId)){
            return  noAssoMessage(responseBody);
        }
        switch (type){
            case "unblock":
                association.setState(1);
                association.setReason(reason);
                assoRepository.save(association);
                break;
            case "block":
                association.setState(0);
                association.setReason(reason);
                assoRepository.save(association);
        }

        return responseBody.setAndGetResponsebody(new HashMap(),"","");
    };
    public ResponseBody handleAsoociationApplication(Integer user_id, String type, Application app ){
        ResponseBody responseBody = new ResponseBody();
        switch (type) {
            case "accept": //create association
                switch (app.getType()) {
                    case "createAssociation":
                        if (true) {    //有权限，待修改
                            List<String> tags =new ArrayList();
                            MyObject assoInfo = app.getObject();
                            createAssociation(assoInfo.getName(),assoInfo.getDescription(),assoInfo.getTags(),assoInfo.getLogo());
                            return responseBody.setAndGetResponsebody(new HashMap(), "", "");
                        }
                        return responseBody.setAndGetResponsebody(null, "noAuthorized", "用户未提供身份验证凭据，或者没有通过身份验证");
                    case "unblockAssociation":
                        if (true) {
                            MyObject blockInfo = app.getObject();
                            Association asso = assoRepository.findById(blockInfo.getAssoId()).get();
                            asso.setState(1);

                            assoRepository.save(asso);
                            return responseBody.setAndGetResponsebody(new HashMap(), "", "");
                        }
                        return responseBody.setAndGetResponsebody(null, "noAuthorized", "用户未提供身份验证凭据，或者没有通过身份验证");
                }
            case "refuse":
            case "cancel":
                if (true) {
                    return responseBody.setAndGetResponsebody(new HashMap(), "", "");
                }
                return responseBody.setAndGetResponsebody(null, "noAuthorized", "用户未提供身份验证凭据，或者没有通过身份验证");
            default:
                return responseBody.setAndGetResponsebody(null, "invalidType", "无效的申请类型");
        }
    }
}