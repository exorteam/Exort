package exort.associationmanager.service;

import java.util.List;

import exort.api.http.assomgr.entity.AssociationFilterParams;
import exort.api.http.common.entity.PagedData;
import exort.associationmanager.entity.Association;


public interface AssociationService{
    public Association getAssociation(String assoId);

    public PagedData<Association> getAssociationsInBatch(List<String> ids,Integer pn,Integer ps);

    public PagedData<Association> listAssociations(AssociationFilterParams params, Integer pageNum, Integer pageSize);

    public Association createAssociation(String name, String description, List<String> tags, String logo);

    public Association createAssociationWithId(String assoId, String name,String description,List<String> tags,String logo);

    public boolean deleteAssociation(String assoId );

    public Association editAssociation(String assoId, String name,String description,List<String> tags,String logo);

    public  boolean patchAssociation(String assoId,String type,String descripion);

    //public  boolean handleAsoociationApplication(Long user_id, String type, Application<MyObject> app );

}
