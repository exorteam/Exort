package exort.associationmanager.service;

import java.util.List;

import com.sun.nio.sctp.Association;

import exort.associationmanager.entity.Article;
import exort.associationmanager.entity.ArticleApplication;

public interface AssociationService{
    public Association createAssociation(Association asso);
    public boolean deleteAssociation(Int assoId );
    public boolean editAssociation(Association asso);
    public boolean setAssociationBlock(Int assoId);
    public boolean setAssociationActive(Int assoId);
    public Association getAssociation(Int assoId);
    public List<Association> listAssociation(AssociationFilterParams params);

}