package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.savadevel.wthl.model.Menu;

import java.time.LocalDate;
import java.util.List;

import static ru.savadevel.wthl.model.Menu.MENU_RESTAURANT_AND_DISH;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @EntityGraph(MENU_RESTAURANT_AND_DISH)
    List<Menu> getAllByDateOrderByRestaurantNameAscDishNameAsc(LocalDate date);

    @EntityGraph(MENU_RESTAURANT_AND_DISH)
    Menu getById(Integer id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Menu WHERE id=:id")
    int delete(@Param("id") int id);
}