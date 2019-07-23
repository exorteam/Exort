package exort.apiserver.service;

import exort.apiserver.serviceimpl.AssociationServiceImpl;
import org.springframework.stereotype.Service;

@Service

public interface AssociationManagerService {

    public AssociationServiceImpl.Response<AssociationServiceImpl.Association> getAssociation(String assoId);

    public AssociationServiceImpl.Response<AssociationServiceImpl.AssociationList> listAssociations(String _body, Integer pageNum, Integer pageSize);

    public AssociationServiceImpl.Response<AssociationServiceImpl.Association> createAssociation(String _body);

    public AssociationServiceImpl.Response<Object> deleteAssociation(String assoId );

    public AssociationServiceImpl.Response<AssociationServiceImpl.Association> editAssociation(String assoId, String _body);

    public AssociationServiceImpl.Response<Object> patchAssociation(String assoId, String _body);

    public AssociationServiceImpl.Response<Object> handleAsoociationApplication(String _body);

}
