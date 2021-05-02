package ru.savadevel.wthl.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.model.Votes;
import ru.savadevel.wthl.repository.MenuRepository;
import ru.savadevel.wthl.repository.VoteRepository;
import ru.savadevel.wthl.to.VoteTo;
import ru.savadevel.wthl.util.VoteUtil;

import javax.validation.Valid;
import java.util.List;

import static ru.savadevel.wthl.util.validation.ValidationUtil.assureIdConsistent;
import static ru.savadevel.wthl.util.validation.ValidationUtil.checkNotFoundWithId;
import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;
import static ru.savadevel.wthl.web.WebUtil.*;

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

    // TODO now REST query like: resource/subresource, maybe must be like: resource/{resource-id}/subresource
    @GetMapping(PART_REST_URL_MENUS)
    public List<Menu> getMenusOnCurrentDate() {
        return menuRepository.getAllByDate(getVotingDay().getNowDate());
    }

    @GetMapping(PART_REST_URL_VOTES)
    public List<Votes> getAmountVotesForRestaurantsOnCurrentDate() {
        return voteRepository.getAmountVotesForRestaurants(getVotingDay().getNowDate());
    }

    @GetMapping(PART_REST_URL_VOTES + "/{voteId}")
    public Vote getVoteById(@PathVariable Integer voteId) {
        return getById(voteId, voteRepository);
    }

    @PostMapping(value = PART_REST_URL_VOTES, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createVote(@Valid @RequestBody VoteTo voteTo) {
        // TODO return ID restaurant without his name
        return add(VoteUtil.createNewFromTo(voteTo), REST_URL + PART_REST_URL_VOTES, voteRepository);
    }

    @PatchMapping(value = PART_REST_URL_VOTES + "/{voteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@Valid @RequestBody VoteTo voteTo, @PathVariable Integer voteId) {
        Vote vote = VoteUtil.updateFromTo(new Vote(), voteTo);
        assureIdConsistent(vote, voteId);
        checkNotFoundWithId(voteRepository.save(vote), vote.id());
    }
}
