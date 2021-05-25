package ru.savadevel.wthl.web;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.savadevel.wthl.model.VoteResult;
import ru.savadevel.wthl.repository.VoteRepository;
import ru.savadevel.wthl.security.SecurityUtil;

import java.util.List;

import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;

@Slf4j
@RestController
@AllArgsConstructor
@RequestMapping(value = VoteResultRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteResultRestController {
    public static final String REST_URL = "/rest/vote-results";

    private final VoteRepository voteRepository;

    @Cacheable(value = "votes")
    @GetMapping
    public List<VoteResult> getAmountVotesForRestaurantsOnCurrentDate() {
        log.info("getAmountVotesForRestaurantsOnCurrentDate for user '{}'", SecurityUtil.authUserId());
        return voteRepository.getAmount(getVotingDay().getNowDate());
    }
}
