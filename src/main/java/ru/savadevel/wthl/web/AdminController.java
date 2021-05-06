package ru.savadevel.wthl.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savadevel.wthl.model.Dish;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.repository.DishRepository;
import ru.savadevel.wthl.repository.MenuRepository;
import ru.savadevel.wthl.repository.RestaurantRepository;
import ru.savadevel.wthl.to.MenuTo;
import ru.savadevel.wthl.util.MenuUtil;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

import static ru.savadevel.wthl.web.WebUtil.*;

@RestController
@RequestMapping(value = AdminController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminController {
    public static final String REST_URL = "/rest/admin";

    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    public AdminController(DishRepository dishRepository, RestaurantRepository restaurantRepository, MenuRepository menuRepository) {
        this.dishRepository = dishRepository;
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
    }

    @GetMapping(PART_REST_URL_DISHES)
    public List<Dish> getDishesAll() {
        // TODO called twice at startup
        return dishRepository.findAll();
    }

    @GetMapping(PART_REST_URL_RESTAURANTS)
    public List<Restaurant> getRestaurantsAll() {
        return restaurantRepository.findAll();
    }

    @GetMapping(PART_REST_URL_RESTAURANTS + "/{restaurantId}" + PART_REST_URL_MENUS)
    public List<Menu> getMenusAll(@PathVariable Integer restaurantId) {
        // TODO data is taken from the database after 4 requests
        return menuRepository.getAllByRestaurantId(restaurantId);
    }

    @GetMapping(value = PART_REST_URL_RESTAURANTS + "/{restaurantId}" + PART_REST_URL_MENUS, params = "date")
    public List<Menu> getMenuRestaurantByDate(@PathVariable Integer restaurantId, @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // TODO data is taken from the database after 4 requests
        return menuRepository.getAllByRestaurantIdAndDate(restaurantId, date);
    }

    @GetMapping(PART_REST_URL_DISHES + "/{dishId}")
    public Dish getDishById(@PathVariable Integer dishId) {
        return dishRepository.getById(dishId);
    }

    @GetMapping(PART_REST_URL_RESTAURANTS + "/{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable Integer restaurantId) {
        return restaurantRepository.getById(restaurantId);
    }

    @GetMapping(PART_REST_URL_MENUS + "/{menuId}")
    public Menu getMenuByIs(@PathVariable Integer menuId) {
        return menuRepository.getById(menuId);
    }

    @PostMapping(value = PART_REST_URL_DISHES, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish>  addDish(@Valid @RequestBody Dish dish) {
        return add(dish, REST_URL + PART_REST_URL_DISHES, dishRepository::save);
    }

    @PostMapping(value = PART_REST_URL_RESTAURANTS, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        return add(restaurant, REST_URL + PART_REST_URL_RESTAURANTS, restaurantRepository::save);
    }

    @PostMapping(value = PART_REST_URL_MENUS, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Menu> addMenu(@Valid @RequestBody MenuTo menuTo) {
        // TODO return ID restaurant without his name, same for dish
        return add(MenuUtil.createNewFromTo(menuTo), PART_REST_URL_MENUS, menuRepository::save);
    }
}
