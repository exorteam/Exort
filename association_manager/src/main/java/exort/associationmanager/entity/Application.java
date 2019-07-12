package exort.associationmanager.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
public class Application<T> {
    @Id
    Integer Id;
    Integer applicantId;
    String type;
    T object;
    String materials[];
    Date createTime;
    Date handleTime;
    String state;//0 unhandled,1 accept,2 refused,3 canceled

    public AssociationInfo getAssociationInfo(){
        return (AssociationInfo) object;
    }
    public BlockInfo getBlockInfo(){
        return  (BlockInfo) object;
    }
}
