package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.savadevel.wthl.model.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {
}
