package ru.savadevel.wthl.web;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.model.Votes;
import ru.savadevel.wthl.repository.MenuRepository;
import ru.savadevel.wthl.repository.RestaurantRepository;
import ru.savadevel.wthl.repository.VoteRepository;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.savadevel.wthl.util.ValidationUtil.checkNew;
import static ru.savadevel.wthl.util.ValidationUtil.checkNotFoundWithId;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    public static final String REST_URL = "/rest/restaurants";

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final VoteRepository voteRepository;

    public RestaurantController(RestaurantRepository restaurantRepository, MenuRepository menuRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping("/{restaurantId}")
    public Restaurant get(@PathVariable Integer restaurantId) {
        return checkNotFoundWithId(restaurantRepository.findById(restaurantId).orElse(null), restaurantId);
    }

    @GetMapping
    public List<Restaurant> getAll() {
        return restaurantRepository.findAll();
    }

    @GetMapping("/{restaurantId}/menus")
    public List<Menu> getMenus(@PathVariable Integer restaurantId) {
        // TODO data is taken from the database after 4 requests
        return menuRepository.getAllByRestaurantId(restaurantId);
    }

    @GetMapping("/votes")
    public List<Votes> getAmountVotesOnRestaurantsByDates() {
        return voteRepository.getAmountVotesForRestaurants(null);
    }

    @GetMapping(value = "/votes", params = "date")
    public List<Votes> getAmountVotesOnRestaurantsByDates(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return voteRepository.getAmountVotesForRestaurants(date);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uri).body(created);
    }
}
