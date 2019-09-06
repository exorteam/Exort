package exort.review_manager.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AssociationMemberSignUp {

    @Field("association_id")
    String associationId;

    @Field("department_ids")
    List<Integer> departmentIds;

}
