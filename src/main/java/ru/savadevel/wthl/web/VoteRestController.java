package ru.savadevel.wthl.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.repository.VoteRepository;
import ru.savadevel.wthl.security.SecurityUtil;
import ru.savadevel.wthl.to.VoteTo;
import ru.savadevel.wthl.util.VoteUtil;

import javax.validation.Valid;

import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;
import static ru.savadevel.wthl.web.WebUtil.add;
import static ru.savadevel.wthl.web.validation.ValidationUtil.assureIdConsistent;
import static ru.savadevel.wthl.web.validation.ValidationUtil.checkNotFoundWithId;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = VoteRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteRestController {
    public static final String REST_URL = "/rest/votes";

    private final VoteRepository voteRepository;

    @GetMapping
    public Vote getVote() {
        log.info("getVote for user '{}'", SecurityUtil.authUserId());
        return voteRepository.getVoteByUserIdAndDate(SecurityUtil.authUserId(), getVotingDay().getNowDate());
    }
    @GetMapping( "/{voteId}")
    public Vote getVoteById(@PathVariable Integer voteId) {
        log.info("getVoteById for voteId {} and user '{}'", voteId, SecurityUtil.authUserId());
        return getVoteOfOwnerById(voteId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createVote(@Valid @RequestBody VoteTo voteTo) {
        log.info("createVote for VoteTo {} and user '{}'", voteTo, SecurityUtil.authUserId());
        return add(VoteUtil.createNewFromTo(voteTo), REST_URL, voteRepository::save);
    }

    @PatchMapping(value = "/{voteId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateVote(@Valid @RequestBody VoteTo voteTo, @PathVariable Integer voteId) {
        log.info("updateVote for VoteTo {}, voteId {} and user '{}'", voteTo, voteId, SecurityUtil.authUserId());
        assureIdConsistent(voteTo, voteId);
        Vote vote = getVoteOfOwnerById(voteTo.id());
        voteRepository.save(VoteUtil.updateFromTo(vote, voteTo));
    }

    private Vote getVoteOfOwnerById(Integer voteId) {
        return checkNotFoundWithId(voteRepository.getVoteByIdAndUserId(voteId, SecurityUtil.authUserId()), voteId);
    }
}
