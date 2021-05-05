package ru.savadevel.wthl.model;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
}
