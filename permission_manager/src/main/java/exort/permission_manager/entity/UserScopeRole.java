package exort.permission_manager.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "scope", "role_id"}))
@Data
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "role"})
public class UserScopeRole {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO, generator="native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "scope")
    private String scope;

    @Column(name = "role_id")
    private String roleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", insertable = false, updatable = false)
    private ExortRole role;

    public UserScopeRole(Long userId, String scope, String roleId) {
        this.userId = userId;
        this.scope = scope;
        this.roleId = roleId;
    }
}
