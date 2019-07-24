package exort.activity.entity;

import java.util.List;

public class Request {

    private int userId;

    private String type;

    private List<Integer> participantIds;

    private List<Integer> realParticipantIds;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
}
