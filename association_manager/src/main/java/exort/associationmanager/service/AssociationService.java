package exort.associationmanager.service;

import java.util.List;


//import ......Materials

import exort.associationmanager.entity.Application;
import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.entity.ResponseBody;

public interface AssociationService{
    public  ResponseBody getAssociation(String assoId);

    public ResponseBody listAssociations(AssociationFilterParams params,Integer pageNum,Integer pageSize);

    public ResponseBody createAssociation(String name, String description, List<String> tags, String logo);

    public ResponseBody deleteAssociation(String assoId );

    public ResponseBody editAssociation(String assoId, String name,String description,List<String> tags,String logo);

    public  ResponseBody patchAssociation(String assoId,String type,String descripion);

    public  ResponseBody handleAsoociationApplication(String user_id, String type, Application app );


//    public  boolean createTestData();

}