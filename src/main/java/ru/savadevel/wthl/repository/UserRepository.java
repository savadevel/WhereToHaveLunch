package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savadevel.wthl.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
