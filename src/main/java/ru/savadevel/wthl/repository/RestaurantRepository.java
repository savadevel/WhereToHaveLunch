package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.savadevel.wthl.model.Restaurant;

@Repository
@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
    Restaurant getById(Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant WHERE id=:id")
    int delete(@Param("id") int id);
}
