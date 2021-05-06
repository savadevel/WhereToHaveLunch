package ru.savadevel.wthl.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.repository.VoteRepository;

import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_VOTE;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
    private static final Logger log = LoggerFactory.getLogger(ProfileController.class);
    public static final String REST_URL = "/rest/profile";

    private final VoteRepository voteRepository;

    public ProfileController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping(PART_REST_URL_VOTE)
    public Vote getVote() {
        log.info("getVote for user '{}'", SecurityUtil.authUserId());
        return voteRepository.getVoteByUserUsernameAndDate(SecurityUtil.get().getUsername(), getVotingDay().getNowDate());
    }
}
