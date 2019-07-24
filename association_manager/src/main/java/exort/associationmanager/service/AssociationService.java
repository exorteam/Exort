package exort.associationmanager.service;

import java.util.List;


import exort.api.http.common.entity.ApiResponse;
import exort.associationmanager.entity.Application;
import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.entity.AssociationList;
import org.springframework.stereotype.Service;

@Service
public interface AssociationService{
    public Association getAssociation(String assoId);

    public AssociationList listAssociations(AssociationFilterParams params, Integer pageNum, Integer pageSize);

    public Association createAssociation(String name, String description, List<String> tags, String logo);

    public boolean deleteAssociation(String assoId );

    public ApiResponse editAssociation(String assoId, String name,String description,List<String> tags,String logo);

    public  ApiResponse patchAssociation(String assoId,String type,String descripion);

    public  ApiResponse handleAsoociationApplication(String user_id, String type, Application app );

}