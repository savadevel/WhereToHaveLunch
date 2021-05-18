package ru.savadevel.wthl.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.repository.VoteRepository;

import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_VOTE;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
    public static final String REST_URL = "/rest/profile";

    private final VoteRepository voteRepository;

    @GetMapping(PART_REST_URL_VOTE)
    public Vote getVote() {
        log.info("getVote for user '{}'", SecurityUtil.authUserId());
        return voteRepository.getVoteByUserUsernameAndDate(SecurityUtil.get().getUsername(), getVotingDay().getNowDate());
    }
}
