package ru.savadevel.wthl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@Getter
@ToString(exclude = "votes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User extends AbstractNamedEntity {

    @Setter
    @Column(name = "password", nullable = false, columnDefinition="VARCHAR(255)")
    @NotBlank
    @Size(min = 3, max = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "role"}, name = "user_roles_unique_username_role_idx")})
    @Column(name = "role", nullable = false, columnDefinition="VARCHAR(16)")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id") //https://stackoverflow.com/a/62848296/548473
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonBackReference("user<vote")
    private List<Vote> votes;

    public User(Integer id, String name, String password, Role role, Role... roles) {
        this(id, name, password, EnumSet.of(role, roles));
    }

    public User(Integer id, String name, String password, Collection<Role> roles) {
        super(id, name);
        this.password = password;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }
}
