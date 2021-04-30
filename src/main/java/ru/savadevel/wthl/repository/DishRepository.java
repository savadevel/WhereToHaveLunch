package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.savadevel.wthl.model.Dish;

import java.util.List;

@Transactional(readOnly = true)
public interface DishRepository extends JpaRepository<Dish, Integer> {

    // TODO why does it work to load menus with EntityGraph, but without it an error occurs
    // failed to lazily initialize a collection of role: ru.savadevel.wthl.model.Dish.menus,
    // could not initialize proxy - no Session (through reference chain: java.util.ArrayList[0]->ru.savadevel.wthl.model.Dish["menus"])
    @EntityGraph(attributePaths = {"menus"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("select d from Dish d")
    List<Dish> getWithMenu();
}
