package ru.savadevel.wthl.security;

import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.savadevel.wthl.model.User;
import ru.savadevel.wthl.repository.UserRepository;

@AllArgsConstructor
@Service("basicUserDetailsService")
public class BasicUserDetailsService implements UserDetailsService {
    private final UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User " + username + " is not found");
        }
        return new AuthorizedUser(user);
    }
}
