package exort.comment.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Document
public class Comment implements Serializable {
    @Id
    private String id;
    private int userId;
    private String userName;
    private String type;
    private String commentedId;
    private String content;
    private Date time;
    private List<String> replies;


    public Comment(int userId, String userName, String type, String commentedId, String content) {
        this.userId = userId;
        this.userName = userName;
        this.type = type;
        this.commentedId = commentedId;
        this.content = content;
        this.time = new Date();
        this.replies = new ArrayList<>();
    }
}
