package exort.permission_manager.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "perm_id"}))
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"role", "perm"})
public class RolePerm {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(name = "role_id")
    private String roleId;

    @Column(name = "perm_id")
    private String permId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private ExortRole role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perm_id", insertable = false, updatable = false)
    private ExortPerm perm;

    public RolePerm(String roleId, String permId) {
        this.roleId = roleId;
        this.permId = permId;
    }
}
