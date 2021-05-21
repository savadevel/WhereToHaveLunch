package ru.savadevel.wthl.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savadevel.wthl.model.Dish;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.repository.DishRepository;
import ru.savadevel.wthl.repository.MenuRepository;
import ru.savadevel.wthl.repository.RestaurantRepository;
import ru.savadevel.wthl.security.SecurityUtil;
import ru.savadevel.wthl.to.MenuTo;
import ru.savadevel.wthl.util.MenuUtil;

import javax.validation.Valid;
import java.util.List;

import static ru.savadevel.wthl.web.WebUtil.*;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminRestaurantController {
    public static final String REST_URL = "/rest";

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    @GetMapping(PART_REST_URL_DISHES)
    public List<Dish> getDishesAll() {
        log.info("getDishesAll for user '{}'", SecurityUtil.authUserId());
        return dishRepository.findAll();
    }

    @GetMapping(PART_REST_URL_RESTAURANTS)
    public List<Restaurant> getRestaurantsAll() {
        log.info("getRestaurantsAll for user '{}'", SecurityUtil.authUserId());
        return restaurantRepository.findAll();
    }

    @GetMapping(PART_REST_URL_DISHES + "/{dishId}")
    public Dish getDishById(@PathVariable Integer dishId) {
        log.info("getDishById for dishId {} and user '{}'", dishId, SecurityUtil.authUserId());
        return dishRepository.getById(dishId);
    }

    @GetMapping(PART_REST_URL_RESTAURANTS + "/{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable Integer restaurantId) {
        log.info("getRestaurantById for restaurantId {} and user '{}'", restaurantId, SecurityUtil.authUserId());
        return restaurantRepository.getById(restaurantId);
    }

    @GetMapping(PART_REST_URL_MENUS + "/{menuId}")
    public Menu getMenuById(@PathVariable Integer menuId) {
        log.info("getMenuByIs for menuId {} and user '{}'", menuId, SecurityUtil.authUserId());
        return menuRepository.getById(menuId);
    }

    @PostMapping(value = PART_REST_URL_DISHES, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish>  addDish(@Valid @RequestBody Dish dish) {
        log.info("addDish for Dish {} and user '{}'", dish, SecurityUtil.authUserId());
        return add(dish, REST_URL + PART_REST_URL_DISHES, dishRepository::save);
    }

    @PostMapping(value = PART_REST_URL_RESTAURANTS, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        log.info("addRestaurant for Restaurant {} and user '{}'", restaurant, SecurityUtil.authUserId());
        return add(restaurant, REST_URL + PART_REST_URL_RESTAURANTS, restaurantRepository::save);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @PostMapping(value = PART_REST_URL_MENUS, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> addMenu(@Valid @RequestBody MenuTo menuTo) {
        log.info("addMenu for MenuTo {} and user '{}'", menuTo, SecurityUtil.authUserId());
        return add(MenuUtil.createNewFromTo(menuTo), PART_REST_URL_MENUS, menuRepository::save);
    }

    @CacheEvict(value = "menus", allEntries = true)
    @DeleteMapping( PART_REST_URL_DISHES + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteDish(@PathVariable int id) {
        log.info("deleteDish for id {} and user '{}'", id, SecurityUtil.authUserId());
        delete(id, dishRepository::delete);
    }

    @DeleteMapping( PART_REST_URL_RESTAURANTS + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteRestaurant(@PathVariable int id) {
        log.info("deleteRestaurant for id {} and user '{}'", id, SecurityUtil.authUserId());
        delete(id, restaurantRepository::delete);
    }

    @DeleteMapping( PART_REST_URL_MENUS + "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteMenu(@PathVariable int id) {
        log.info("deleteMenu for id {} and user '{}'", id, SecurityUtil.authUserId());
        delete(id, menuRepository::delete);
    }
}
