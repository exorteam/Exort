package exort.api.http.types;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ApplicationTest {

    @Data
    static class MyObject {
        private int id;
        private String desc;
    }

    ObjectMapper mapper = new ObjectMapper();
    Application<MyObject> application;

    @BeforeEach
    void JSONToObject() throws IOException {
        application = mapper.readValue("{\"id\":1, \"applicantId\":2, \"type\":\"signup\", \"materialIds\": [], \"createdTime\": null, \"handledTime\":null, \"state\":\"pending\",\"object\":{\"id\":1,\"desc\":\"hello world\"}}", new TypeReference<Application<MyObject>>() {});
    }

    @Test
    void TestJSONToObject() {
        assertEquals(application.getId(), 1);
        assertEquals(application.getApplicantId(), 2);
        assertEquals(application.getType(), "signup");
        assertEquals(application.getMaterialIds().size(), 0);
        assertNull(application.getCreatedTime());
        assertNull(application.getHandledTime());
        assertEquals(application.getObject().getId(), 1);
        assertEquals(application.getObject().getDesc(), "hello world");
    }

    @Test
    void EncodeDecode() throws IOException {
        String json = mapper.writeValueAsString(application);
        assertEquals(mapper.readValue(json, new TypeReference<Application<MyObject>>() {}), application);
    }

}