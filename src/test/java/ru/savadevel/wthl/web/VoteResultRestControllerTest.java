package ru.savadevel.wthl.web;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static ru.savadevel.wthl.UserTestData.user1;
import static ru.savadevel.wthl.VotesTestData.VOTES_TO_MATCHER;
import static ru.savadevel.wthl.VotesTestData.votesTo1;
import static ru.savadevel.wthl.VotesTestData.votesTo2;

class VoteResultRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_VOTE_RESULTS = VoteResultRestController.REST_URL + "/";

    @Test
    void getAmountVotesForRestaurantsOnCurrentDate() throws Exception {
        checkGet(URI.create(REST_URL_VOTE_RESULTS), user1, VOTES_TO_MATCHER, votesTo2, votesTo1);
    }
}