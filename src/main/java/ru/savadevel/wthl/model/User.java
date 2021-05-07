package ru.savadevel.wthl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@ToString(exclude = "votes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class User implements Persistable<String> {

    @Getter
    @Setter
    @Id
    @GeneratedValue
    @Column(name = "username", unique = true, nullable = false, columnDefinition="VARCHAR(32)")
    @NotBlank
    @Size(min = 3, max = 32)
    private String username;

    @Getter
    @Setter
    @Column(name = "password", nullable = false, columnDefinition="VARCHAR(32)")
    @NotBlank
    @Size(min = 3, max = 32)
    private String password;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition="VARCHAR(16)")
    @Access(AccessType.FIELD)
    private Role role;

    @Getter
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    @JsonBackReference("user<vote")
    private List<Vote> votes;

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
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
