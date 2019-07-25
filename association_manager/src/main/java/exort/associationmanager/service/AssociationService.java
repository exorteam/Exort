package exort.associationmanager.service;

import exort.api.http.common.entity.PagedData;
import exort.api.http.review.entity.Application;
import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.entity.AssociationList;
import exort.associationmanager.entity.MyObject;

import java.util.List;




public interface AssociationService{
    public Association getAssociation(String assoId);

    public PagedData<Association> listAssociations(AssociationFilterParams params, Integer pageNum, Integer pageSize);

    public Association createAssociation(String name, String description, List<String> tags, String logo);

    public boolean deleteAssociation(String assoId );

    public Association editAssociation(String assoId, String name,String description,List<String> tags,String logo);

    public  boolean patchAssociation(String assoId,String type,String descripion);

    public  boolean handleAsoociationApplication(Long user_id, String type, Application<MyObject> app );

}