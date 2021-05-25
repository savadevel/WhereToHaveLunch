package ru.savadevel.wthl.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.repository.RestaurantRepository;
import ru.savadevel.wthl.security.SecurityUtil;

import javax.validation.Valid;
import java.util.List;

import static ru.savadevel.wthl.web.WebUtil.add;
import static ru.savadevel.wthl.web.WebUtil.delete;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = RestaurantRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantRestController {
    public static final String REST_URL = "/rest/restaurants";

    private final RestaurantRepository restaurantRepository;

    @GetMapping
    public List<Restaurant> getRestaurantsAll() {
        log.info("getRestaurantsAll for user '{}'", SecurityUtil.authUserId());
        return restaurantRepository.findAll();
    }

    @GetMapping("/{restaurantId}")
    public Restaurant getRestaurantById(@PathVariable Integer restaurantId) {
        log.info("getRestaurantById for restaurantId {} and user '{}'", restaurantId, SecurityUtil.authUserId());
        return restaurantRepository.getById(restaurantId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> addRestaurant(@Valid @RequestBody Restaurant restaurant) {
        log.info("addRestaurant for Restaurant {} and user '{}'", restaurant, SecurityUtil.authUserId());
        return add(restaurant, REST_URL, restaurantRepository::save);
    }

    @CacheEvict(value = {"menus", "votes"}, allEntries = true)
    @DeleteMapping( "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void  deleteRestaurant(@PathVariable int id) {
        log.info("deleteRestaurant for id {} and user '{}'", id, SecurityUtil.authUserId());
        delete(id, restaurantRepository::delete);
    }
}
