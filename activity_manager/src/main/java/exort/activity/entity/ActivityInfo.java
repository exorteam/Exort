package exort.activity.entity;

import exort.api.http.activity.entity.ActivityTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "activity")
public class ActivityInfo implements Serializable {
    @Id
    private String id;
    private List<String> associationIds;
    private Date createTime;
    private Date publishTime;
    private Date lastPublishTime;
    private Date lastModifyTime;
    private String title;
    private String content;
    private ActivityTime signupTime;
    private ActivityTime time;
    private int publishState = 1;
    private int signupState = 1;
    private int state = 1;
    private boolean ifReview;
    private boolean ifOnlyMem;
    private int maxParticipants;
    private List<Integer> materialTemplateIds;
    private List<Integer> participantIds;
    private List<Integer> realParticipantIds;
    private List<String> tags;
    private String image;

    public ActivityInfo(List<String> associationIds, ActivityTime signupTime, ActivityTime time, String title, String content,
                        boolean ifReview, boolean ifOnlyMem, int maxParticipants, List<Integer> materialTemplateIds, List<String> tags, String image) {
        this.associationIds = associationIds;
        this.createTime = new Date();
        this.publishTime = null;
        this.lastPublishTime = null;
        this.lastModifyTime = new Date();
        this.signupTime = signupTime;
        this.time = time;
        this.title = title;
        this.content = content;
        this.ifReview = ifReview;
        this.ifOnlyMem = ifOnlyMem;
        this.maxParticipants = maxParticipants;
        this.materialTemplateIds = materialTemplateIds;
        this.tags = tags;
        this.image = image;
    }
}
