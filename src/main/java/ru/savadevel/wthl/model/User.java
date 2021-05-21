package ru.savadevel.wthl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.*;
import org.springframework.data.domain.Persistable;
import org.springframework.util.CollectionUtils;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.*;

@Getter
@ToString(exclude = "votes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User implements Persistable<String> {

    @Setter
    @Id
    @GeneratedValue
    @Column(name = "username", unique = true, nullable = false, columnDefinition="VARCHAR(128)")
    @NotBlank
    @Size(min = 3, max = 128)
    private String username;

    @Setter
    @Column(name = "password", nullable = false, columnDefinition="VARCHAR(255)")
    @NotBlank
    @Size(min = 3, max = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "username"),
            uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "role"}, name = "user_roles_unique_username_role_idx")})
    @Column(name = "role", nullable = false, columnDefinition="VARCHAR(16)")
    @ElementCollection(fetch = FetchType.EAGER)
    @JoinColumn(name = "username") //https://stackoverflow.com/a/62848296/548473
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Role> roles;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonBackReference("user<vote")
    private List<Vote> votes;

    public User(String username, String password, Role role, Role... roles) {
        this(username, password, EnumSet.of(role, roles));
    }

    public User(String username, String password, Collection<Role> roles) {
        this.username = username;
        this.password = password;
        setRoles(roles);
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = CollectionUtils.isEmpty(roles) ? EnumSet.noneOf(Role.class) : EnumSet.copyOf(roles);
    }

    @Override
    public String getId() {
        return username;
    }

    @Override
    public boolean isNew() {
        return username == null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }
}
