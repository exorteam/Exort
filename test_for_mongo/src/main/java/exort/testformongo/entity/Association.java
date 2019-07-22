package exort.testformongo.entity;

import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class Association {
    @Id
    private String id;
    private String name;
    private String description;
    private String logo;
}
