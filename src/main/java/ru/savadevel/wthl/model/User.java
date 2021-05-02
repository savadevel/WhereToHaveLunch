package ru.savadevel.wthl.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "users_unique_name_idx")})
public class User extends AbstractNamedEntity {

    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 5, max = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    // TODO what other options are there not to have n + 1
    private Role role;

    public User(Integer id, String name, String password, Role role) {
        super(id, name);
        this.password = password;
        this.role = role;
    }
}
