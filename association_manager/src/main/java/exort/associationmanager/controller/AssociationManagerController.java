package exort.associationmanager.controller;


import exort.associationmanager.entity.Application;
import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
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
    private static class CreateAssoRequestInfo {
        private String name;
        private String description;
        private List<String> tags;
        private  String logo;
    }

    @Data
    private static class EditAssoRequestInfo {
        private Integer assoId;
        private String name;
        private String description;
        private List<String> tags;
        private  String logo;
    }
    @Data

    private static class BlockAssoRequestInfo {
        private  Integer assoId;
        private String reason;
    }

    @Data
    private static class ApplicationAssoRequestInfo {
        private Integer userId;
        private String operation;
        private Application app;
    }


    @GetMapping("/associations")
    public List<Association> listAssociations(@RequestBody AssociationFilterParams body ){
        return service.listAssociation(body);
    };

    @PostMapping("/association")
    public Association createAssociation(@RequestBody CreateAssoRequestInfo body){
        return  service.createAssociation(body.getName(),body.getDescription(),body.getTags(),body.getLogo());
    };
    @DeleteMapping("/association")
    public boolean deleteAssociation(@RequestParam Integer assoId ){
        return  service.deleteAssociation(assoId);
    }
    @PutMapping("/association")
    public Association editAssociation(@RequestBody EditAssoRequestInfo body){
        return service.editAssociation(body.getAssoId(), body.getName(),body.getDescription(),body.getTags(),body.getLogo());
    }
    @PutMapping("/illegal_association")
    public boolean blockAssociation(@RequestBody BlockAssoRequestInfo body){
        return service.blockAssociation(body.getAssoId(),body.getReason());
    }
    @PutMapping("/legal_association")

    public boolean unblockAssociation(@RequestParam Integer assoId){
        return service.unblockAssociation(assoId);
    }
    @PostMapping("/association_application")
    public boolean handleAssociationApplication(@RequestBody ApplicationAssoRequestInfo body){
        return service.handleAsoociationApplication(body.getUserId(),body.getOperation(),body.getApp());
    };
    @RequestMapping("/test")
    public String handleAssociationApplication() {
        return "Hello world!";
    };
    @GetMapping("/test1")
    public String AssociationApplication() {
        return "Hello world!";
    }
}