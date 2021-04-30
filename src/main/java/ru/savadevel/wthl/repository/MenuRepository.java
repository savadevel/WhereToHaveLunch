package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.savadevel.wthl.model.Menu;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> getAllByRestaurantId(Integer restaurantId);
    List<Menu> getAllByRestaurantIdAndDate(Integer restaurantId, @NotNull LocalDate date);
    List<Menu> getAllByDate(LocalDate date);
}
