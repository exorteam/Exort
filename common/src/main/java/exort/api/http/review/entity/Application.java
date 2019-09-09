package exort.api.http.review.entity;

import exort.api.http.review.entity.details.AssociationMemberSignUp;
import exort.api.http.review.entity.details.ActivitySignUp;
import exort.api.http.review.entity.details.AssociationInfo;
import exort.api.http.review.entity.receipt.AssociationMemberReceipt;
import exort.api.http.review.entity.receipt.NormalReceipt;

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
public class Application<Details, Receipt> {

    private Long id;
    private Date createdTime;
    private Date handledTime;
    private String state;

    private Long applicantId;
    private String type;
    private Details details;
    private List<String> materialIds;

    private Long operator;
    private Receipt receipt;

    public Application(Long applicantId, String type, Details details) {
        this.applicantId = applicantId;
        this.type = type;
        this.details = details;
    }

    public Application(Long applicantId, String type, Details details, List<String> materialIds) {
        this.applicantId = applicantId;
        this.type = type;
        this.details = details;
        this.materialIds = materialIds;
    }

    public static Application<AssociationInfo, NormalReceipt> createAssociation(Long applicantId, AssociationInfo info) {
        return new Application<>(applicantId, "AssociationInfo", info);
    }

    public static Application<ActivitySignUp, NormalReceipt> signUpActivity(Long applicantId, ActivitySignUp signUp) {
        return new Application<>(applicantId, "ActivitySignUp", signUp);
    }

    public static Application<AssociationMemberSignUp, AssociationMemberReceipt> signUpAssociationMember(Long applicantId, AssociationMemberSignUp signUp) {
        return new Application<>(applicantId, "AssociationMemberSignUp", signUp);
    }

}
