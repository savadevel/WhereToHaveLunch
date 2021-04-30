package ru.savadevel.wthl.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.model.Votes;
import ru.savadevel.wthl.repository.MenuRepository;
import ru.savadevel.wthl.repository.VoteRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.savadevel.wthl.web.WebUtils.*;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestaurantController {
    public static final String REST_URL = "/rest/restaurants";

    private final MenuRepository menuRepository;
    private final VoteRepository voteRepository;

    public RestaurantController(MenuRepository menuRepository, VoteRepository voteRepository) {
        this.menuRepository = menuRepository;
        this.voteRepository = voteRepository;
    }

    @GetMapping(REST_URL + PART_REST_URL_MENUS)
    public List<Menu> getMenusOnCurrentDate() {
        return menuRepository.getAllByDate(LocalDate.now());
    }

    @GetMapping(REST_URL + PART_REST_URL_VOTES)
    public List<Votes> getAmountVotesOnRestaurantsOnCurrentDate() {
        return voteRepository.getAmountVotesForRestaurants(LocalDate.now());
    }

    @GetMapping(REST_URL +  PART_REST_URL_VOTES + "/{voteId}")
    public Vote getVoteById(@PathVariable Integer voteId) {
        return getById(voteId, voteRepository);
    }

    @PostMapping(REST_URL + "/{restaurantId}" + PART_REST_URL_VOTES)
    public Vote createVote(@PathVariable Integer restaurantId) {
        return null;
    }

    @PutMapping(REST_URL + "/{restaurantId}" + PART_REST_URL_VOTES)
    public void updateVote(@PathVariable Integer restaurantId) {
        return;
    }
}
