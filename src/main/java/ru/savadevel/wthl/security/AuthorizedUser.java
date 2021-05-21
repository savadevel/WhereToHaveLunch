package ru.savadevel.wthl.security;

import lombok.Getter;
import lombok.ToString;
import ru.savadevel.wthl.model.User;

import java.io.Serial;

@Getter
@ToString
public class AuthorizedUser extends org.springframework.security.core.userdetails.User {
    @Serial
    private static final long serialVersionUID = 1L;

    private User user;

    public AuthorizedUser(User user) {
        super(user.getUsername(), user.getPassword(), true, true, true, true, user.getRoles());
        this.user = user;
    }
}
