package exort.associationmanager.entity;

import java.util.*;
import org.springframework.data.annotation.Id;
import lombok.Data;

@Data
public class AssociationApplication{
    
    @Id
    private int id;
    private int user_id;
    private Association asso;
    private Date apply_time;
    private int state; //unhandled,accepted,refused,canceled

    public  name() {
        
    }
    
}