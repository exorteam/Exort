package exort.associationmanager.service;

import java.util.List;


//import ......Materials

import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationApplication;
import exort.associationmanager.entity.AssociationApplicationFilterParams;
import exort.associationmanager.entity.AssociationFilterParams;

public interface AssociationService{
    public List<Association> listAssociation(AssociationFilterParams params);

    public Association createAssociation(String name,String description,String tags[],String logo);

    public boolean deleteAssociation(Integer assoId );

    public  boolean blockAssociation(Integer assoId,String reason);

    public boolean editAssociation(Integer assoId, String name,String description,String tags[],String logo);

    public  boolean createAssociationApplication(String name,String description,String tags[],String logo,String materials[]);

    public  List<AssociationApplication> listAssociationApplication(AssociationApplicationFilterParams params);

    public  boolean createUnblockApplication(Integer assoId,String reason,Integer applicantId);

    public boolean setAssociationActive(Integer assoId);

    public boolean refuseUnblockApplication(int assoId);



}