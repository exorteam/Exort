package exort.associationmanager.service;

import java.util.List;


//import ......Materials

import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;

public interface AssociationService{
    public List<Association> listAssociation(AssociationFilterParams params);

    public Association createAssociation(String name,String description,String tags[],String logo);

    public boolean deleteAssociation(int assoId );

    public boolean editAssociation(int assoId, String name,String description,String tags[],String logo);

    public  boolean blockAssociation(int assoId,String reason);

    public  boolean unblockAssciation(int assoId);

    public boolen
}