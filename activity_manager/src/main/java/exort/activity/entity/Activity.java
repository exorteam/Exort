package exort.activity.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import java.util.List;

public class Activity {
    @Id
    @GeneratedValue
    private Long id;

    private List<Long> associationIds;

    private String createTime;

    private String publishTime;

    private String lastPublishTime;

    private String lastModifyTime;

    private String title;

    private String content;

    private ActivityTime signupTime;

    private ActivityTime time;

    private int publishState;

    private int signupState;

    private int state;

    private boolean ifReview;

    private boolean ifOnlyMembers;

    private int maxParticipants;

    private List<Long> materialTemplateIds;

    private List<Long> participantIds;

    private List<Long> realParticipantIds;

    private List<String> tags;

    public Activity(
            List<Long> associationIds,
            String createTime,
            String publishTime,
            String lastPublishTime,
            String lastModifyTime,
            ActivityTime signupTime,
            ActivityTime time,
            String title,
            String content,
            int publishState,
            int signupState,
            int state,
            boolean ifReview,
            boolean ifOnlyMembers,
            int maxParticipants,
            List<Long> materialTemplateIds,
            List<Long> participantIds,
            List<Long> realParticipantIds,
            List<String> tags
    ) {
        this.associationIds = associationIds;
        this.createTime = createTime;
        this.publishTime = publishTime;
        this.lastPublishTime = lastPublishTime;
        this.lastModifyTime = lastModifyTime;
        this.signupTime = signupTime;
        this.time = time;
        this.title = title;
        this.content = content;
        this.publishState = publishState;
        this.signupState = signupState;
        this.state = state;
        this.ifReview = ifReview;
        this.ifOnlyMembers = ifOnlyMembers;
        this.maxParticipants = maxParticipants;
        this.materialTemplateIds = materialTemplateIds;
        this.participantIds = participantIds;
        this.realParticipantIds = realParticipantIds;
        this.tags = tags;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getAssociationIds() {
        return associationIds;
    }

    public void setAssociationIds(List<Long> associationIds) {
        this.associationIds = associationIds;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getLastPublishTime() {
        return lastPublishTime;
    }

    public void setLastPublishTime(String lastPublishTime) {
        this.lastPublishTime = lastPublishTime;
    }

    public String getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(String lastModifyTime) {
        this.lastModifyTime = lastModifyTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ActivityTime getSignupTime() {
        return signupTime;
    }

    public void setSignupTime(ActivityTime signupTime) {
        this.signupTime = signupTime;
    }

    public ActivityTime getTime() {
        return time;
    }

    public void setTime(ActivityTime time) {
        this.time = time;
    }

    public int getPublishState() {
        return publishState;
    }

    public void setPublishState(int publishState) {
        this.publishState = publishState;
    }

    public int getSignupState() {
        return signupState;
    }

    public void setSignupState(int signupState) {
        this.signupState = signupState;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isIfReview() {
        return ifReview;
    }

    public void setIfReview(boolean ifReview) {
        this.ifReview = ifReview;
    }

    public boolean isIfOnlyMembers() {
        return ifOnlyMembers;
    }

    public void setIfOnlyMembers(boolean ifOnlyMembers) {
        this.ifOnlyMembers = ifOnlyMembers;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public List<Long> getMaterialTemplateIds() {
        return materialTemplateIds;
    }

    public void setMaterialTemplateIds(List<Long> materialTemplateIds) {
        this.materialTemplateIds = materialTemplateIds;
    }

    public List<Long> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Long> participantIds) {
        this.participantIds = participantIds;
    }

    public List<Long> getRealParticipantIds() {
        return realParticipantIds;
    }

    public void setRealParticipantIds(List<Long> realParticipantIds) {
        this.realParticipantIds = realParticipantIds;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
