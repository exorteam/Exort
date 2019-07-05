package exort.associationmanager.serviceimpl;

import java.util.Date;
import java.util.List;

import javax.xml.ws.ServiceMode;

import org.springframework.beans.facyory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.repository.AssociationRepository;
import exort.associationmanager.service.AssociationService;

@Service
public class ArticleServiceImpl implements AssociationService{
    @Autowired
    private AssociationRepository repository;

    public Association createAssociation(Association asso){
        if(asso.getName() == null) || asso.getDescription() == null || asso.getLogo() == null){
            return null;
        }
        asso.setState(0);

        Integer articleId = 1;
		while(repository.existsById(articleId)){++articleId;}
		article.setId(articleId);

    }
}