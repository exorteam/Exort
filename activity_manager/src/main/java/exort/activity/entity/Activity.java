package exort.activity.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import java.util.List;

public class Activity {
    @Id
    @GeneratedValue
    private Long id;

    private List<Long> association_ids;

    private String create_time;

    private String publish_time;

    private String last_publish_time;

    private String last_modify_time;

    private String signup_start_time;

    private String signup_end_time;

    private String start_time;

    private String end_time;

    private String title;

    private String content;

    private int state;

    private boolean review;

    private boolean only_members;

    private int max_participants;

    private List<Long> material_template_ids;

    private List<Long> participants_ids;

    public Activity(
            String create_time,
            String publish_time,
            String last_publish_time,
            String last_modify_time,
            String signup_start_time,
            String signup_end_time,
            String start_time,
            String end_time,
            String title,
            String content,
            int state,
            boolean review,
            boolean only_members,
            int max_participants
    ){
        this.create_time = create_time;
        this.publish_time = publish_time;
        this.last_publish_time = last_publish_time;
        this.last_modify_time = last_modify_time;
        this.signup_start_time = signup_start_time;
        this.signup_end_time = signup_end_time;
        this.start_time = start_time;
        this.end_time = end_time;
        this.title = title;
        this.content = content;
        this.state = state;
        this.review = review;
        this.only_members = only_members;
        this.max_participants = max_participants;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Long> getAssociation_ids() {
        return association_ids;
    }

    public void setAssociation_ids(List<Long> association_ids) {
        this.association_ids = association_ids;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getLast_publish_time() {
        return last_publish_time;
    }

    public void setLast_publish_time(String last_publish_time) {
        this.last_publish_time = last_publish_time;
    }

    public String getLast_modify_time() {
        return last_modify_time;
    }

    public void setLast_modify_time(String last_modify_time) {
        this.last_modify_time = last_modify_time;
    }

    public String getSignup_start_time() {
        return signup_start_time;
    }

    public void setSignup_start_time(String signup_start_time) {
        this.signup_start_time = signup_start_time;
    }

    public String getSignup_end_time() {
        return signup_end_time;
    }

    public void setSignup_end_time(String signup_end_time) {
        this.signup_end_time = signup_end_time;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public boolean isReview() {
        return review;
    }

    public void setReview(boolean review) {
        this.review = review;
    }

    public boolean isOnly_members() {
        return only_members;
    }

    public void setOnly_members(boolean only_members) {
        this.only_members = only_members;
    }

    public int getMax_participants() {
        return max_participants;
    }

    public void setMax_participants(int max_participants) {
        this.max_participants = max_participants;
    }

    public List<Long> getMaterial_template_ids() {
        return material_template_ids;
    }

    public void setMaterial_template_ids(List<Long> material_template_ids) {
        this.material_template_ids = material_template_ids;
    }

    public List<Long> getParticipants_ids() {
        return participants_ids;
    }

    public void setParticipants_ids(List<Long> participants_ids) {
        this.participants_ids = participants_ids;
    }
}
