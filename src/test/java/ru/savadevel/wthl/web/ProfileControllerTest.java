package ru.savadevel.wthl.web;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savadevel.wthl.UserTestData;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savadevel.wthl.TestUtil.userHttpBasic;
import static ru.savadevel.wthl.UserTestData.*;
import static ru.savadevel.wthl.VoteTestData.VOTE_MATCHER;
import static ru.savadevel.wthl.VoteTestData.vote1;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_VOTES;

class ProfileControllerTest extends AbstractControllerTest {
    private static final String REST_URL_VOTES = ProfileController.REST_URL + PART_REST_URL_VOTES + "/";

    @Test
    void getVote() throws Exception {
        checkGet(URI.create(REST_URL_VOTES), user1, VOTE_MATCHER, vote1);
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_VOTES).with(userHttpBasic(admin)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_VOTES).with(userHttpBasic(UserTestData.getNewWithUserRole())))
                .andExpect(status().isUnauthorized());
    }
}