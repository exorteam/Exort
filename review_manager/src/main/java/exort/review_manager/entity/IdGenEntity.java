package exort.review_manager.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
public class IdGenEntity {

    @Field("name")
    private String name;

    @Field("max_id")
    private Long maxId;

}
