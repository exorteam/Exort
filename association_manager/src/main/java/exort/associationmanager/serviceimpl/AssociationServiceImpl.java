package exort.associationmanager.serviceimpl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exort.associationmanager.entity.Association;
import exort.associationmanager.entity.AssociationFilterParams;
import exort.associationmanager.repository.AssociationRepository;
import exort.associationmanager.service.AssociationService;

@Service
public class AssociationServiceImpl implements AssociationService{
    @Autowired
    private AssociationRepository repository;


    public Association createAssociation(Association asso){
        if(asso. == null) || asso.getDescription() == null || asso.getLogo() == null){
            return null;
        }
        asso.setState(0);

        Integer articleId = 1;
		while(repository.existsById(articleId)){++articleId;}
		article.setId(articleId);

    }
}