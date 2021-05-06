package ru.savadevel.wthl.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    private static final Logger log = LoggerFactory.getLogger(RestaurantController.class);
    public static final String REST_URL = "/rest/restaurants";

    private final MenuRepository menuRepository;
    private final VoteRepository voteRepository;

    public RestaurantController(MenuRepository menuRepository, VoteRepository voteRepository) {
        this.menuRepository = menuRepository;
        this.voteRepository = voteRepository;
    }

    // TODO now REST query like: resource/subresource, maybe must be like: resource/{resource-id}/subresource
    @GetMapping(PART_REST_URL_MENUS)
    @Cacheable("restaurants")
    public List<Menu> getMenusOnCurrentDate() {
        log.info("getMenusOnCurrentDate for user '{}'", SecurityUtil.authUserId());
        return menuRepository.getAllByDate(getVotingDay().getNowDate());
    }

    @GetMapping(PART_REST_URL_VOTES)
    @Cacheable("votes")
    public List<Votes> getAmountVotesForRestaurantsOnCurrentDate() {
        log.info("getAmountVotesForRestaurantsOnCurrentDate for user '{}'", SecurityUtil.authUserId());
        return voteRepository.getAmount(getVotingDay().getNowDate());
    }

    @GetMapping(PART_REST_URL_VOTES + "/{voteId}")
    public Vote getVoteById(@PathVariable Integer voteId) {
        log.info("getVoteById for voteId {} and user '{}'", voteId, SecurityUtil.authUserId());
        return checkNotFoundWithId(voteRepository.getVoteByIdAndUserUsername(voteId, SecurityUtil.get().getUsername()), voteId);
    }

    @CacheEvict(value = "votes", allEntries = true)
    @PostMapping(value = PART_REST_URL_VOTES, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createVote(@Valid @RequestBody VoteTo voteTo) {
        log.info("createVote for VoteTo {} and user '{}'", voteTo, SecurityUtil.authUserId());
        // TODO return ID restaurant without his name
        return add(VoteUtil.createNewFromTo(voteTo), REST_URL + PART_REST_URL_VOTES, this::saveOrUpdate);
    }

    @CacheEvict(value = "votes", allEntries = true)
    @PatchMapping(value = PART_REST_URL_VOTES + "/{voteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@Valid @RequestBody VoteTo voteTo, @PathVariable Integer voteId) {
        log.info("updateVote for VoteTo {}, voteId {} and user '{}'", voteTo, voteId, SecurityUtil.authUserId());
        Vote vote = VoteUtil.updateFromTo(new Vote(), voteTo);
        assureIdConsistent(vote, voteId);
        checkNotFoundWithId(saveOrUpdate(vote), vote.id());
    }

    @Transactional
    protected Vote saveOrUpdate(Vote vote) {
        if (!vote.isNew() && voteRepository.getVoteByIdAndUserUsername(vote.id(), vote.getUser().getUsername()) == null) {
            return null;
        }
        return voteRepository.save(vote);
    }
}
