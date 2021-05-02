package ru.savadevel.wthl.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.repository.VoteRepository;

import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_VOTES;

@RestController
@RequestMapping(value = ProfileController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ProfileController {
    public static final String REST_URL = "/rest/profile";

    private final VoteRepository voteRepository;

    public ProfileController(VoteRepository voteRepository) {
        this.voteRepository = voteRepository;
    }

    @GetMapping(PART_REST_URL_VOTES)
    public Vote getVote() {
        return voteRepository.getVoteByUserIdAndDate(SecurityUtil.authUserId(), getVotingDay().getNowDate());
    }
}
