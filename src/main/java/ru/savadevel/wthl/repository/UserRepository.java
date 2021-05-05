package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.savadevel.wthl.model.User;

@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, String> {
}
