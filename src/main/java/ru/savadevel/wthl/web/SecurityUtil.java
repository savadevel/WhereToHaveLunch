package ru.savadevel.wthl.web;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static java.util.Objects.requireNonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SecurityUtil {
    public static UserDetails safeGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) {
            return null;
        }
        Object principal = auth.getPrincipal();
        return (principal instanceof UserDetails) ? (UserDetails) principal : null;
    }

    public static UserDetails get() {
        return requireNonNull(safeGet(), "No authorized user found");
    }

    public static String authUserId() {
        return get().getUsername();
    }
}
