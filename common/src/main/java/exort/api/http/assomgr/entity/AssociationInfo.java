package exort.api.http.assomgr.entity;
import lombok.Data;
import java.util.List;

@Data
public class AssociationInfo {
    private String name;
    private String description;
    private List<String> tags;
    private  String logo;
    private AssociationInfo data;
}
