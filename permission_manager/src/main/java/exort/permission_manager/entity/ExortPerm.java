package exort.permission_manager.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"rps"})
public class ExortPerm {
    @Id
    private String id;

    private String category;

    private String description;

    @OneToMany(mappedBy = "perm", fetch = FetchType.LAZY)
    private List<RolePerm> rps;

    public ExortPerm(String id, String category, String description) {
        this.id = id;
        this.category = category;
        this.description = description;
    }
}
