package exort.associationmanager.serviceimpl;

import java.util.List;

import exort.associationmanager.entity.Application;
import exort.associationmanager.entity.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
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
                responseBody.setData(associations);
                responseBody.setError("");
                responseBody.setMessage("");
                return responseBody;
            }
        }

        String keyword = params.getKeyword();
        if(keyword != null){
            associations.removeIf(association -> !association.getName().contains(keyword)||!association.getDescription().contains(keyword));
            if(associations.isEmpty()){
                responseBody.setData(associations);
                responseBody.setError("");
                responseBody.setMessage("");
                return responseBody;
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
                responseBody.setData(associations);
                responseBody.setError("");
                responseBody.setMessage("");
                return responseBody;
            }
        }
        responseBody.setData(associations);
        responseBody.setError("");
        responseBody.setMessage("");
        return responseBody;
    }


    public ResponseBody createAssociation(String name,String description,List<String> tags,String logo){
        ResponseBody responseBody = new ResponseBody();

        if( name == null || description == null || tags == null || logo == null){
            responseBody.setData(null);
            responseBody.setError("invalidFormat");
            responseBody.setMessage("无效的申请格式");
            return null;
        }

        Association association= new Association();

        association.setName(name);
        association.setDescription(description);
        association.setLogo(logo);
        association.setTags(tags);
        association.setState(2);
        association.setReason(null);

        Integer associationId = 1;
        while(assoRepository.existsById(associationId)){++associationId;}
        association.setId(associationId);

        assoRepository.save(association);
        responseBody.setData(association);
        responseBody.setMessage("");
        responseBody.setError("");
        return responseBody;
    };

    public ResponseBody deleteAssociation(Integer assoId ){
        ResponseBody responseBody = new ResponseBody();
        if(!assoRepository.existsById(assoId)) return false;
        assoRepository.deleteById(assoId);
        return true;
    };

    public ResponseBody editAssociation(Integer assoId, String name, String description, List<String> tags, String logo){
        ResponseBody responseBody = new ResponseBody();
        if(!assoRepository.existsById(assoId)) return null;

        Association association = assoRepository.findById(assoId).get();
        association.setDescription(description);
        association.setTags(tags);
        association.setName(name);
        association.setLogo(logo);
        assoRepository.save(association);
        return association;
    };

    public  ResponseBody blockAssociation(Integer assoId,String reason){
        ResponseBody responseBody = new ResponseBody();
        if(!assoRepository.existsById(assoId)) return false;

        Association association = assoRepository.findById(assoId).get();
        association.setState(1);
        association.setReason(reason);
        assoRepository.save(association);
        return true;
    };


    public ResponseBody handleAsoociationApplication(Integer user_id, String type, Application app ){
//        public static final int UNHANDLED = 0 ;
//        public static final int ACCEPT = 1 ;
//        public static final int REFUSED = 2 ;
//        public static final int CANCELED = 3 ;
//        public static final int CANCELED = 3 ;

        switch (type){
            case "accept": //create association
                switch (app.getType()){
                    case "create":
                        if(true){    //有权限，待修改
                             assoRepository.save(app.getAssociation());

                             return true;
                        }
                        return false;
                    case "unblock":
                        if(true){
                            Association asso = app.getAssociation();
                            asso.setState(1);
                            assoRepository.save(asso);
                        }
                        return false;
                }

            case  "refuse": //unblock association
                switch (app.getType()){
                    case "create":
                        if(true){    //有权限，待修改
                            return true;
                        }
                        return false;
                    case "unblock":
                        if(true){
                            return true;
                        }
                        return false;
                }
            case  "cancel":
                switch (app.getType()){
                    case "create":
                        if(true){    //有权限，待修改
                            return true;
                        }
                        return false;
                    case "unblock":
                        if(true){
                            return true;
                        }
                        return false;
                };
        };
        return false;
    };



}