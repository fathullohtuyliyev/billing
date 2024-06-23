package uz.ichange.billing.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import uz.ichange.billing.model.custom.RoleType;


@Table(name = "roles")
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role extends BaseEntity implements GrantedAuthority {
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private RoleType name;
    @Column(name = "description")
    private String description;
    @Column(name = "deleted_by_id", insertable = false, updatable = false)
    private Long deletedById;

    @Override
    public String getAuthority() {
        return name.name();
    }
}
