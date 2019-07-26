package exort.api.http.activity.entity;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Activity {
    private String id;
    private List<Integer> associationIds;
    private Date createTime;
    private Date publishTime;
    private Date lastPublishTime;
    private Date lastModifyTime;
    private String title;
    private String content;
    private ActivityTime signupTime;
    private ActivityTime time;
    private int publishState;
    private int signupState;
    private int state;
    private boolean ifReview;
    private boolean ifOnlyMem;
    private int maxParticipants;
    private List<Integer> materialTemplateIds;
    private List<Integer> participantIds;
    private List<Integer> realParticipantIds;
    private List<String> tags;
    private String image;

    public Activity(List<Integer> associationIds, ActivityTime signupTime, ActivityTime time, String title, String content,
                    int publishState, int signupState, int state, boolean ifReview, boolean ifOnlyMem, int maxParticipants,
                    List<Integer> materialTemplateIds, List<Integer> participantIds, List<Integer> realParticipantIds,
                    List<String> tags, String image) {
        this.associationIds = associationIds;
        this.createTime = new Date();
        this.publishTime = new Date();
        this.lastPublishTime = new Date();
        this.lastModifyTime = new Date();
        this.signupTime = signupTime;
        this.time = time;
        this.title = title;
        this.content = content;
        this.publishState = publishState;
        this.signupState = signupState;
        this.state = state;
        this.ifReview = ifReview;
        this.ifOnlyMem = ifOnlyMem;
        this.maxParticipants = maxParticipants;
        this.materialTemplateIds = materialTemplateIds;
        this.participantIds = participantIds;
        this.realParticipantIds = realParticipantIds;
        this.tags = tags;
        this.image = image;
    }
}
