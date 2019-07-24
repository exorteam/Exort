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
}
