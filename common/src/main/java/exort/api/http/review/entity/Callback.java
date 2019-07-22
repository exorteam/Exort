package exort.api.http.review.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Callback {
    private Long id;
    private String type;
    private String event;
    private String endpoint;
    private String url;
    private String description;

    public Callback(String type, String event, String endpoint, String url, String description) {
        this.type = type;
        this.event = event;
        this.endpoint = endpoint;
        this.url = url;
        this.description = description;
    }
}
