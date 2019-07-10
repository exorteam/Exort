package exort.associationmanager.serviceimpl;

import java.util.Date;
import java.util.List;

import exort.associationmanager.entity.Application;
import exort.associationmanager.repository.AssociationApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.repository.AssociationRepository;
import exort.associationmanager.service.AssociationService;

@Service
public class AssociationServiceImpl implements AssociationService{
    @Autowired
    private AssociationRepository assoRepository;
    private AssociationApplicationRepository assoAppRepository;

    public List<Association> listAssociation(AssociationFilterParams params){
        List<Association> associations = assoRepository.findAll();

        Integer state = params.getState();

        if(state != null){
            associations.removeIf(association -> !state.equals(association.getState()));
            if(associations.isEmpty())return associations;
        }

        String keyword = params.getKeyword();
        if(keyword != null){
            associations.removeIf(association -> !association.getName().contains(keyword)||!association.getDescription().contains(keyword));
            if(associations.isEmpty())return associations;
        }

        List<String> tags = params.getTags();
        if(tags != null){
            associations.removeIf(association -> haveTags(tags));
            if(associations.isEmpty())return associations;
        }

        return associations;
    };

    public Association createAssociation(String name,String description,String tags[],String logo){

    };

    public boolean deleteAssociation(int assoId ){

    };

    public boolean editAssociation(int assoId, String name,String description,String tags[],String logo){

    };

    public  boolean blockAssociation(int assoId,String reason){

    };

    public  boolean unblockAssciation(int assId){

    };

    public List<Association> listAllBlockedAssociation(){

    };

    public List<Association> listAllUnblockedAssociation(){

    };

    public  boolean handleCreateAsoociationApplication(Integer user_id, Integer type, Application app ){

    };

    public  boolean handleUnblockAsoociationApplication(Integer user_id, Integer type, Application app ){
        
    };




}