package exort.associationmanager.serviceimpl;

import java.util.Date;
import java.util.List;

import exort.associationmanager.entity.AssociationApplication;
import exort.associationmanager.entity.AssociationApplicationFilterParams;
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
        List<Association> articles = assoRepository.findAll();

        Integer state = params.getState();

        if(state != null){
            articles.removeIf(article -> !state.equals(article.getState()));
            if(articles.isEmpty())return articles;
        }

        String keyword = params.getKeyword();
        if(keyword != null){
            articles.removeIf(article -> !article.getTitle().contains(keyword)||!article.getContent().contains(keyword));
            if(articles.isEmpty())return articles;
        }

        Integer authorId = params.getAuthorId();
        if(authorId != null){
            articles.removeIf(article -> !article.getAuthors().contains(authorId));
            if(articles.isEmpty())return articles;
        }

        return articles;
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

    public  boolean createAssociationApplication(String name,String description,String tags[],String logo,String materials[]){

    };

    public  boolean applyUnblockAssociation(int assoId,String reason,int applicantId){

    };

    public  List<AssociationApplication> listAssociationApplication(AssociationApplicationFilterParams params){
        List<Association> articles = assoRepository.findAll();

        Integer state = params.getState();
        if(state != null){
            articles.removeIf(article -> !state.equals(article.getState()));
            if(articles.isEmpty())return articles;
        }

        Integer createMethod = params.getCreateMethod();
        if(createMethod != null){
            articles.removeIf(article -> !createMethod.equals(article.getCreateMethod()));
            if(articles.isEmpty())return articles;
        }

        Date startTime = params.getStartTime();
        if(startTime != null){
            articles.removeIf(article -> startTime.after(article.getLastPublishTime()));
            if(articles.isEmpty())return articles;
        }

        Date endTime = params.getEndTime();
        if(endTime != null){
            articles.removeIf(article -> endTime.before(article.getLastPublishTime()));
            if(articles.isEmpty())return articles;
        }

        String keyword = params.getKeyword();
        if(keyword != null){
            articles.removeIf(article -> !article.getTitle().contains(keyword)||!article.getContent().contains(keyword));
            if(articles.isEmpty())return articles;
        }

        Integer authorId = params.getAuthorId();
        if(authorId != null){
            articles.removeIf(article -> !article.getAuthors().contains(authorId));
            if(articles.isEmpty())return articles;
        }

        return articles;
    };

    public  boolean approveApplication(int appId){

    };

    public  boolean refuseApplication(int appId){

    };

    public  boolean cancelApplication(int appId){

    };


}