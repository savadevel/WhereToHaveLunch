package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.savadevel.wthl.model.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<User, String> {
    @Query("FROM User u INNER JOIN FETCH u.roles WHERE u.username=:username")
    User getByUsername(@NotBlank @Size(min = 3, max = 128) @Param("username") String username);
}
