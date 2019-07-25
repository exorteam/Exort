
package exort.api.http.assomgr.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Association {
    private Integer id;
    private String logo;
    private String description;
    private List<String> tags;
    private Integer state; //0 blocked,1 active
    private String name;
    private String reason;
}
