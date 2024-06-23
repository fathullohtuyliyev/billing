package uz.ichange.billing.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.userdetails.UserDetails;

import uz.ichange.billing.model.custom.AppLangs;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public abstract class User extends BaseEntity implements UserDetails {

    @Column(unique = true)
    private String phoneNumber;
    private String password;
    private String email;
    private Boolean isActive = false;
    @Column(columnDefinition = "BOOLEAN default TRUE")
    private boolean notification = true;
    @Column(length = 32, columnDefinition = "varchar(32) default 'RU'")
    @Enumerated(EnumType.STRING)
    private AppLangs lang = AppLangs.RU;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = {@JoinColumn(name = "users_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roles;
    private Integer smsCode;
    private String fullName;
    private Integer age;
    private String profileImageUrl;
    @Column(name = "device_token")
    private String deviceToken;
}