package exort.associationmanager.service;

import java.util.List;


//import ......Materials

import exort.associationmanager.entity.Application;
import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;

public interface AssociationService{
    public List<Association> listAssociation(AssociationFilterParams params);

    public Association createAssociation(String name,String description,List<String> tags,String logo);

    public boolean deleteAssociation(Integer assoId );

    public boolean editAssociation(Integer assoId, String name,String description,List<String> tags,String logo);

    public  boolean blockAssociation(Integer assoId,String reason);

    public  boolean unblockAssciation(Integer assoId);

    public  boolean handleAsoociationApplication(Integer user_id, Integer type, Application app );

}