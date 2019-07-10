package exort.associationmanager.controller;

import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.service.AssociationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AssociationController{
    @Autowired
    private AssociationService service;

    @PostMapping("/create")
    public Association createArticle(@RequestBody Association asso){
        return service.createAssociation(asso);
    }

    @GetMapping("/delete")
    public boolean deleteAssociation(@RequestParam int assoId ){
        return service.deleteAssociation(assoId);
    }

    @PostMapping("/edit")
    public boolean editAssociation(@RequestParam int assoId,@RequestBody Association asso){
        return service.editAssociation(assoId,asso);
    }

    @PostMapping("/block")
    public boolean setAssociationBlock(@RequestParam int assoId){
        return service.setAssociationBlock(assoId);
    }
    @PostMapping("/set_active")
    public boolean setAssociationActive(@RequestParam int assoId){
        return service.setAssociationBlock(assoId);
    }
    @PostMapping("/set_app_ok")
    public boolean setAssociationAppOk(@RequestParam int assoAppId){
        return service.setAssociationAppOk(assoAppId);
    }
    @PostMapping("/set_app_fault")
    public boolean setAssociationAppFault(@RequestParam int assoAppId){
        return service.setAssociationAppFault(assoAppId);
    }

    @GetMapping("/get")
    public Association getAssociation(@RequestParam int assoId){
        return service.getAssociation(assoId);
    }

    @PostMapping("/list")
    public List<Association> listAssociation(@RequestBody AssociationFilterParams params){
        return service.listAssociation(params);
    }

    @

}