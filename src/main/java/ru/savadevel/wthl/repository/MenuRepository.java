package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savadevel.wthl.model.Menu;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Integer> {
    List<Menu> getAllByRestaurantId(Integer restaurantId);
}
