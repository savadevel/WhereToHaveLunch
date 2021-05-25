package ru.savadevel.wthl.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savadevel.wthl.model.Dish;
import ru.savadevel.wthl.repository.DishRepository;
import ru.savadevel.wthl.security.SecurityUtil;

import javax.validation.Valid;
import java.util.List;

import static ru.savadevel.wthl.web.WebUtil.add;
import static ru.savadevel.wthl.web.WebUtil.delete;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController {
    public static final String REST_URL = "/rest/dishes";

    private final DishRepository dishRepository;

    @GetMapping
    public List<Dish> getDishesAll() {
        log.info("getDishesAll for user '{}'", SecurityUtil.authUserId());
        return dishRepository.findAll();
    }

    @GetMapping("/{dishId}")
    public Dish getDishById(@PathVariable Integer dishId) {
        log.info("getDishById for dishId {} and user '{}'", dishId, SecurityUtil.authUserId());
        return dishRepository.getById(dishId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish>  addDish(@Valid @RequestBody Dish dish) {
        log.info("addDish for Dish {} and user '{}'", dish, SecurityUtil.authUserId());
        return add(dish, REST_URL, dishRepository::save);
    }

    @CacheEvict(value = {"menus", "votes"}, allEntries = true)
    @DeleteMapping( "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteDish(@PathVariable int id) {
        log.info("deleteDish for id {} and user '{}'", id, SecurityUtil.authUserId());
        delete(id, dishRepository::delete);
    }
}
