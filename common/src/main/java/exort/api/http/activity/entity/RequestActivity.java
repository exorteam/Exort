package exort.api.http.activity.entity;

import java.util.List;

import lombok.Data;

@Data
public class RequestActivity {
    private int userId;
    private String type;
    private List<Integer> participantIds;
    private List<Integer> realParticipantIds;
}
