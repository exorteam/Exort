package exort.testformongo.serviceImpl;

import exort.testformongo.entity.Association;
import exort.testformongo.service.TestService;
import exort.testformongo.test_mongo.A;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl implements TestService {
    @Autowired
    private  A  assoRepository;
    public boolean createAssociation(String name,String logo,String description){
        if( name == null || description == null || logo == null){
            return false;
        }

        Association association= new Association();

        association.setName(name);
        association.setDescription(description);
        association.setLogo(logo);

        String associationId = new ObjectId().toString();
        association.setId(associationId);

        assoRepository.save(association);
        return true;
    };
}
