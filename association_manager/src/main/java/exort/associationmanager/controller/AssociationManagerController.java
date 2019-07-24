package exort.associationmanager.controller;


import exort.associationmanager.entity.*;
import exort.associationmanager.entity.ResponseBody;
import exort.associationmanager.service.AssociationService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping

public class AssociationManagerController{

    @Autowired
    private AssociationService service;

    @Data
    private static class PatchAssociationInfo {
        private String description;
        private String type;
    }

    @Data
    private static class ApplicationAssociationInfo {
        private String userId;
        private String event;
        private Application application;

    }
//origin
    @GetMapping("/associations")
    public ResponseBody listAssociations(@RequestBody AssociationFilterParams body,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize ){
        return service.listAssociations(body,pageNum,pageSize);
    }
//naive
//    @GetMapping("/associations")
//    public ResponseBody listAssociations(@RequestParam("keyword") String keyword,@RequestParam("tags") String tags,@RequestParam("state") Integer state,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize ){
//        AssociationFilterParams body=new AssociationFilterParams();
//        body.setKeyword(keyword);
//        List<String> laozizhenshicaole = new ArrayList<String>(Arrays.asList(tags.split(",")));
//        body.setTags(laozizhenshicaole);
//        body.setState(state);
//        return service.listAssociations(body,pageNum,pageSize);
//    }
//professional


    @GetMapping("/associations/{assoId}")
    public ResponseBody getAssociation(@PathVariable(value="assoId") String assoId){
        return service.getAssociation(assoId);
    }
//origin
    @PostMapping("/associations")
    public ResponseBody createAssociation(@RequestBody AssociationInfo body){
        return  service.createAssociation(body.getName(),body.getDescription(),body.getTags(),body.getLogo());
    }

//    @PostMapping("/associations")
//    public ResponseBody createAssociation(@RequestBody AssociationInfo body){
//        return  service.createAssociation(body.getData().getName(),body.getData().getDescription(),body.getData().getTags(),body.getData().getLogo());
//    }

    @DeleteMapping("/associations/{assoId}")
    public ResponseBody deleteAssociation(@PathVariable(value="assoId") String assoId ){
        return  service.deleteAssociation(assoId);
    }
// origin
    @PutMapping("/associations/{assoId}")
    public ResponseBody editAssociation(@RequestBody AssociationInfo body,@PathVariable(value="assoId") String assoId ){
        return service.editAssociation(assoId, body.getName(),body.getDescription(),body.getTags(),body.getLogo());
    }

//    @PutMapping("/associations/{assoId}")
//    public ResponseBody editAssociation(@RequestBody AssociationInfo body,@PathVariable(value="assoId") String assoId ){
//        return service.editAssociation(assoId, body.getData().getName(),body.getData().getDescription(),body.getData().getTags(),body.getData().getLogo());
//    }

    @PutMapping("/associations/{assoId}/state")
    public ResponseBody patchAssociation(@RequestBody PatchAssociationInfo body,@PathVariable(value="assoId") String assoId ){
        return service.patchAssociation(assoId,body.getType(),body.getDescription());
    }

    @PostMapping("/callback")
    public ResponseBody handleAssociationApplication(@RequestBody ApplicationAssociationInfo body){
        return service.handleAsoociationApplication(body.getUserId(),body.getEvent(),body.getApplication());
    }

//    @GetMapping("/test")
//    public boolean createData(){
//        return service.createTestData();
//    }

}
