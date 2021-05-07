package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.savadevel.wthl.model.Menu;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

import static ru.savadevel.wthl.model.Menu.MENU_RESTAURANT_AND_DISH;

@Repository
@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    @EntityGraph(MENU_RESTAURANT_AND_DISH)
    List<Menu> getAllByRestaurantId(Integer restaurantId);

    @EntityGraph(MENU_RESTAURANT_AND_DISH)
    List<Menu> getAllByRestaurantIdAndDate(Integer restaurantId, @NotNull LocalDate date);

    @EntityGraph(MENU_RESTAURANT_AND_DISH)
    List<Menu> getAllByDate(LocalDate date);

    @EntityGraph(MENU_RESTAURANT_AND_DISH)
    Menu getById(Integer id);
}