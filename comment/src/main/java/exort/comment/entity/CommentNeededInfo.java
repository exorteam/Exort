package exort.comment.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentNeededInfo {
    int userId;
    String userName;
    String type;
    String commentedId;
    String content;


    public CommentNeededInfo(int userId, String userName, String content) {
        this.userId = userId;
        this.userName = userName;
        this.content = content;
        this.commentedId = "";
        this.type = "";
    }
}
