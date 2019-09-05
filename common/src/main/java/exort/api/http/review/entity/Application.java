package exort.api.http.review.entity;

import exort.api.http.review.entity.details.AssociationMemberSignUp;
import exort.api.http.review.entity.details.ActivitySignUp;
import exort.api.http.review.entity.details.AssociationInfo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Application<T> {

    private Long id;
    private Date createdTime;
    private Date handledTime;
    private String state;

    private Long applicantId;
    private String type;
    private T details;
    private List<String> materialIds;

    public Application(Long applicantId, String type, T details) {
        this.applicantId = applicantId;
        this.type = type;
        this.details = details;
    }

    public Application(Long applicantId, String type, T details, List<String> materialIds) {
        this.applicantId = applicantId;
        this.type = type;
        this.details = details;
        this.materialIds = materialIds;
    }

    public static Application<AssociationInfo> createAssociation(Long applicantId, AssociationInfo info) {
        return new Application<>(applicantId, "AssociationInfo", info);
    }

    public static Application<ActivitySignUp> signUpActivity(Long applicantId, ActivitySignUp signUp) {
        return new Application<>(applicantId, "ActivitySignUp", signUp);
    }

    public static Application<AssociationMemberSignUp> signUpAssociationMember(Long applicantId, AssociationMemberSignUp signUp) {
        return new Application<>(applicantId, "AssociationMemberSignUp", signUp);
    }

}
