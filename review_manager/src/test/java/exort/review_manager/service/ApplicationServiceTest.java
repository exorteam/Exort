package exort.review_manager.service;

import exort.review_manager.entity.Application;
import exort.review_manager.entity.IdGenEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class ApplicationServiceTest {

    @Autowired
    private MongoTemplate mt;

    @BeforeEach
    void setUp() {
        mt.dropCollection(Application.class);
        mt.dropCollection(IdGenEntity.class);
    }

}
