package exort.activity.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document(collection = "activity")
public class Activity {

    @Id
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
        this.id = (new ObjectId()).toString();
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

    public List<Integer> getAssociationIds() {
        return associationIds;
    }

    public void setAssociationIds(List<Integer> associationIds) {
        this.associationIds = associationIds;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    public Date getLastPublishTime() {
        return lastPublishTime;
    }

    public void setLastPublishTime(Date lastPublishTime) {
        this.lastPublishTime = lastPublishTime;
    }

    public Date getLastModifyTime() {
        return lastModifyTime;
    }

    public void setLastModifyTime(Date lastModifyTime) {
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

    public boolean isIfOnlyMem() {
        return ifOnlyMem;
    }

    public void setIfOnlyMem(boolean ifOnlyMem) {
        this.ifOnlyMem = ifOnlyMem;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }

    public void setMaxParticipants(int maxParticipants) {
        this.maxParticipants = maxParticipants;
    }

    public List<Integer> getMaterialTemplateIds() {
        return materialTemplateIds;
    }

    public void setMaterialTemplateIds(List<Integer> materialTemplateIds) {
        this.materialTemplateIds = materialTemplateIds;
    }

    public List<Integer> getParticipantIds() {
        return participantIds;
    }

    public void setParticipantIds(List<Integer> participantIds) {
        this.participantIds = participantIds;
    }

    public List<Integer> getRealParticipantIds() {
        return realParticipantIds;
    }

    public void setRealParticipantIds(List<Integer> realParticipantIds) {
        this.realParticipantIds = realParticipantIds;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
