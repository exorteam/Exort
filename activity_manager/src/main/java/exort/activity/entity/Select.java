package exort.activity.entity;

import java.util.List;

public class Select {
    private Long associationId;
    private String keyword;
    private List<String> tags;
    private TimeRange createTime;
    private TimeRange startTime;
    private TimeRange signupTime;
    private int publishState;
    private int signupState;
    private int state;
    private int ifReview;
    private int ifOnlyMem;

    public Long getAssociationId() {
        return associationId;
    }

    public void setAssociationId(Long associationId) {
        this.associationId = associationId;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public TimeRange getCreateTime() {
        return createTime;
    }

    public void setCreateTime(TimeRange createTime) {
        this.createTime = createTime;
    }

    public TimeRange getStartTime() {
        return startTime;
    }

    public void setStartTime(TimeRange startTime) {
        this.startTime = startTime;
    }

    public TimeRange getSignupTime() {
        return signupTime;
    }

    public void setSignupTime(TimeRange signupTime) {
        this.signupTime = signupTime;
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

    public int getIfReview() {
        return ifReview;
    }

    public void setIfReview(int ifReview) {
        this.ifReview = ifReview;
    }

    public int getIfOnlyMem() {
        return ifOnlyMem;
    }

    public void setIfOnlyMem(int ifOnlyMem) {
        this.ifOnlyMem = ifOnlyMem;
    }
}
