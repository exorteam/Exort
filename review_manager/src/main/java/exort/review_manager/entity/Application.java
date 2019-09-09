package exort.review_manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application {

    public final static String STATE_PENDING = "pending";
    public final static String STATE_ACCEPTED = "accepted";
    public final static String STATE_REFUSED = "refused";
    public final static String STATE_CANCELED = "canceled";

    public final static String TYPE_ACTIVITY_SIGN_UP = "ActivitySignUp";
    public final static String TYPE_ASSOCIATION_MEMBER_SIGN_UP = "AssociationMemberSignUp";
    public final static String TYPE_ASSOCIATION_INFO = "AssociationInfo";

    @Id
    private Long id;

    @Indexed
    @Field("applicant_id")
    private Long applicantId;

    @Indexed
    @Field("type")
    private String type;

    @Field("details")
    private Object details;

    @Field("material_ids")
    private List<String> materialIds;

    @Indexed(direction = IndexDirection.DESCENDING)
    @Field("created_time")
    private Date createdTime;

    @Field("handled_time")
    private Date handledTime;

    @Indexed
    @Field("state")
    private String state;

    @Field("operator")
    private Long operator;

    @Field("receipt")
    private Object receipt;

    public Application(Long id, Long applicantId, String type, Object details) {
        this.id = id;
        this.applicantId = applicantId;
        this.type = type;
        this.details = details;
        this.materialIds = Collections.emptyList();
        this.createdTime = new Date();
        this.handledTime = null;
        this.state = STATE_PENDING;
        this.operator = null;
        this.receipt = null;
    }

}
