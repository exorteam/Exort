package exort.apiserver.controller;


import exort.apiserver.service.AssociationManagerService;
import exort.apiserver.serviceimpl.AssociationServiceImpl;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping

public class AssociationManagerController{

    @Autowired
    private AssociationManagerService service;


    @GetMapping("/associations")
    public AssociationServiceImpl.Response<AssociationServiceImpl.AssociationList> listAssociations(@RequestParam String _body, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize ){
        return service.listAssociations(_body,pageNum,pageSize);
    }


    @GetMapping("/associations/{assoId}")
    public AssociationServiceImpl.Response<AssociationServiceImpl.Association> getAssociation(@PathVariable(value="assoId") String assoId){
        return service.getAssociation(assoId);
    }

    @PostMapping("/associations")
    public AssociationServiceImpl.Response<AssociationServiceImpl.Association> createAssociation(@RequestParam String _body){
        return  service.createAssociation(_body);
    }

    @DeleteMapping("/associations/{assoId}")
    public AssociationServiceImpl.Response<Object> deleteAssociation(@PathVariable(value="assoId") String assoId ){
        return  service.deleteAssociation(assoId);
    }

    @PutMapping("/associations/{assoId}")
    public AssociationServiceImpl.Response<AssociationServiceImpl.Association> editAssociation(@RequestParam String _body, @PathVariable(value="assoId") String assoId ){
        return service.editAssociation(assoId, _body);
    }


    @PatchMapping("/associations/{assoId}")
    public AssociationServiceImpl.Response<Object> patchAssociation(@RequestParam String _body, @PathVariable(value="assoId") String assoId ){
        return service.patchAssociation(assoId,_body);
    }

    @PostMapping("/callback")
    public AssociationServiceImpl.Response<Object> handleAssociationApplication(@RequestParam String _body){
        return service.handleAsoociationApplication(_body);
    }

}
