package exort.review_manager.repository;

import exort.review_manager.entity.IdGenEntity;
import exort.review_manager.repository.impl.IdGeneratorRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
class IdGeneratorRepositoryTest {

    @Autowired
    private IdGeneratorRepositoryImpl idgen;

    @Autowired
    private MongoTemplate mt;

    @BeforeEach
    void setUp() {
        mt.dropCollection(IdGenEntity.class);
    }

    @Test
    void nextId() {
        long id = idgen.nextId("id1");
        assertEquals(1L, id);
        id = idgen.nextId("id2");
        assertEquals(1L, id);
        id = idgen.nextId("id1");
        assertEquals(2L, id);
        id = idgen.nextId("id1");
        assertEquals(3L, id);
    }

    @Test
    void resetId() {
        long id = idgen.resetId("id1", 1L);
        assertEquals(1L, id);
        id = idgen.resetId("id2", 1L);
        assertEquals(1L, id);
        id = idgen.resetId("id1", 10L);
        assertEquals(10L, id);
        id = idgen.resetId("id1", 100L);
        assertEquals(100L, id);
    }
}