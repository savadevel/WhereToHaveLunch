package ru.savadevel.wthl.web;

import org.junit.jupiter.api.Test;

import java.net.URI;

import static ru.savadevel.wthl.VoteTestData.VOTE_MATCHER;
import static ru.savadevel.wthl.VoteTestData.vote1;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_VOTES;

class ProfileControllerTest extends AbstractControllerTest {
    private static final String REST_URL_VOTES = ProfileController.REST_URL + PART_REST_URL_VOTES + "/";

    @Test
    void getVote() throws Exception {
        checkGet(URI.create(REST_URL_VOTES), VOTE_MATCHER, vote1);
    }
}