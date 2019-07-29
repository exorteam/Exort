package exort.permission_manager.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "rps", "usrs"})
public class ExortRole {
    @Id
    private String id;

    private String category;

    private String description;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<RolePerm> rps;

    @OneToMany(mappedBy = "role", fetch = FetchType.LAZY)
    private List<UserScopeRole> usrs;

    public ExortRole(String id, String category, String description) {
        this.id = id;
        this.category = category;
        this.description = description;
    }

}
