package exort.testformongo.service;


import org.springframework.stereotype.Service;
@Service
public interface TestService {
    boolean createAssociation(String name,String logo,String description);
}
