package ru.savadevel.wthl.model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User  {
    @Id
    @Column(name = "username", nullable = false)
    @NotBlank
    @Size(min = 3, max = 32)
    private String username;

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 3, max = 32)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    // TODO what other options are there not to have n + 1
    private Role role;

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
