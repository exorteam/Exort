package exort.associationmanager.controller;


import exort.associationmanager.entity.Application;
import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.entity.ResponseBody;
import exort.associationmanager.service.AssociationService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping

public class AssociationManagerController{
    @Autowired
    private AssociationService service;

    @Data
    private static class AssoRequestInfo {
        private String name;
        private String description;
        private List<String> tags;
        private  String logo;
    }


    @Data

    private static class PatchAssoRequestInfo {
        private String description;
        private String type;
    }

    @Data
    private static class ApplicationAssoRequestInfo {
        private Integer userId;
        private String operation;
        private Application app;
    }


    @GetMapping("/associations")
    public ResponseBody listAssociations(@RequestBody AssociationFilterParams body,@RequestParam("pageNum") Integer pageNum,@RequestParam("pageSize") Integer pageSize ){
        return service.listAssociations(body,pageNum,pageSize);
    }

    @GetMapping("/associations/{assoId}")
    public ResponseBody getAssociation(@PathVariable(value="assoId") Integer assoId){
        return service.getAssociation(assoId);
    }

    @PostMapping("/associations")
    public ResponseBody createAssociation(@RequestBody AssoRequestInfo body){
        return  service.createAssociation(body.getName(),body.getDescription(),body.getTags(),body.getLogo());
    }

    @DeleteMapping("/associations/{assoId}")
    public ResponseBody deleteAssociation(@PathVariable(value="assoId") Integer assoId ){
        return  service.deleteAssociation(assoId);
    }

    @PutMapping("/associations/{assoId}")
    public ResponseBody editAssociation(@RequestBody AssoRequestInfo body,@PathVariable(value="assoId") Integer assoId ){
        return service.editAssociation(assoId, body.getName(),body.getDescription(),body.getTags(),body.getLogo());
    }

    @PutMapping("/illegal_association/{assoId}")
    public ResponseBody patchAssociation(@RequestBody PatchAssoRequestInfo body,@PathVariable(value="assoId") Integer assoId ){
        return service.patchAssociation(assoId,body.getType(),body.getDescription());
    }

    @PostMapping("/association_application")
    public ResponseBody handleAssociationApplication(@RequestBody ApplicationAssoRequestInfo body){
        return service.handleAsoociationApplication(body.getUserId(),body.getOperation(),body.getApp());
    }

    @RequestMapping("/test")
    public String handleAssociationApplication() {
        return "Hello world!";
    }

    @GetMapping("/test1")
    public String AssociationApplication() {
        return "Hello world!";
    }
}