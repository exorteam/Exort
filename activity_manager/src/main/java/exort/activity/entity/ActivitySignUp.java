package exort.activity.entity;

import org.springframework.data.annotation.Id;

import javax.persistence.GeneratedValue;
import java.util.List;

public class ActivitySignUp {

    @Id
    @GeneratedValue
    private Long id;

    private Long user_id;

    private Long activity_id;

    private List<Long> material_ids;

    private String create_time;

    private String close_time;

    private int state;

    public ActivitySignUp(Long user_id, Long activity_id, String create_time, String close_time, int state){

        this.user_id = user_id;
        this.activity_id = activity_id;
//        this.material_ids = material_ids;
        this.create_time = create_time;
        this.close_time = close_time;
        this.state = state;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(Long activity_id) {
        this.activity_id = activity_id;
    }

    public List<Long> getMaterial_ids() {
        return material_ids;
    }

    public void setMaterial_ids(List<Long> material_ids) {
        this.material_ids = material_ids;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getClose_time() {
        return close_time;
    }

    public void setClose_time(String close_time) {
        this.close_time = close_time;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
