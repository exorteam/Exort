package exort.associationmanager.service;

import java.util.List;


//import ......Materials

import exort.associationmanager.entity.Application;
import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.entity.ResponseBody;

public interface AssociationService{
    public  ResponseBody getAssociation(Integer assoId);

    public ResponseBody listAssociations(AssociationFilterParams params,Integer pageNum,Integer pageSize);

    public ResponseBody createAssociation(String name, String description, List<String> tags, String logo);

    public ResponseBody deleteAssociation(Integer assoId );

    public ResponseBody editAssociation(Integer assoId, String name,String description,List<String> tags,String logo);

    public  ResponseBody patchAssociation(Integer assoId,String type,String descripion);

    public  ResponseBody handleAsoociationApplication(Integer user_id, String type, Application app );


//    public  boolean createTestData();

}