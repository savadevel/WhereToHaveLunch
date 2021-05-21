package ru.savadevel.wthl.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savadevel.wthl.UserTestData;
import ru.savadevel.wthl.VoteTestData;
import ru.savadevel.wthl.model.Role;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.repository.VoteRepository;
import ru.savadevel.wthl.util.VoteUtil;
import ru.savadevel.wthl.web.json.JsonUtil;

import java.net.URI;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savadevel.wthl.RestaurantTestData.restaurant1;
import static ru.savadevel.wthl.RestaurantTestData.restaurant2;
import static ru.savadevel.wthl.TestUtil.userHttpBasic;
import static ru.savadevel.wthl.UserTestData.*;
import static ru.savadevel.wthl.UserTestData.user4;
import static ru.savadevel.wthl.VoteTestData.*;
import static ru.savadevel.wthl.VotesTestData.*;
import static ru.savadevel.wthl.util.VoteUtil.asTo;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_VOTES;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_VOTE_RESULTS;

class UserRestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL_VOTES = UserRestaurantController.REST_URL + PART_REST_URL_VOTES + "/";
    private static final String REST_URL_VOTE_RESULTS = UserRestaurantController.REST_URL + PART_REST_URL_VOTE_RESULTS + "/";
    private static final LocalDateTime VOTE_TIME_INVALID = LocalDateTime.of(2021, 1, 1, 11, 0, 0, 0);

    @Autowired
    private VoteRepository repository;

    @Test
    void getAmountVotesForRestaurantsOnCurrentDate() throws Exception {
        checkGet(URI.create(REST_URL_VOTE_RESULTS), user1, VOTES_TO_MATCHER, votesTo2, votesTo1);
    }

    @Test
    void getVote() throws Exception {
        checkGet(URI.create(REST_URL_VOTES), user1, VOTE_MATCHER, vote1);
    }

    @Test
    void getVoteById() throws Exception {
        checkGet(URI.create(REST_URL_VOTES + vote1.getId()), user1, VOTE_MATCHER, vote1);
    }

    @Test
    void createVote() throws Exception {
        checkPostTo(URI.create(REST_URL_VOTES), user4, VOTE_TO_MATCHER, VoteTestData.getNew(restaurant1),
                Vote.class, VoteUtil::asTo, (id) -> repository.getVoteByIdAndUserId(id, user4.id()));
    }

    @Test
    void createVoteAfterPossibleUpdate() throws Exception {
        setDateTime(VOTE_TIME_INVALID);
        checkPostTo(URI.create(REST_URL_VOTES), user4, VOTE_TO_MATCHER, VoteTestData.getNew(restaurant1),
                Vote.class, VoteUtil::asTo, (id) -> repository.getVoteByIdAndUserId(id, user4.id()));
    }

    @Test
    void updateVote() throws Exception {
        Vote updated = new Vote(vote1);
        updated.setRestaurant(restaurant2);
        perform(MockMvcRequestBuilders.patch(URI.create(REST_URL_VOTES + updated.id()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(updated)))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isNoContent());

        VOTE_MATCHER.assertMatch(repository.getVoteByIdAndUserId(updated.id(), user1.id()), updated);
    }

    @Test
    void updateWithInvalidTimeForVote() throws Exception {
        Vote updated = new Vote(vote1);
        updated.setRestaurant(restaurant2);
        setDateTime(VOTE_TIME_INVALID);
        perform(MockMvcRequestBuilders.patch(URI.create(REST_URL_VOTES + updated.id()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(updated)))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void getVotesUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_VOTES).with(userHttpBasic(UserTestData.getNew(Role.USER))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_VOTES).with(userHttpBasic(admin)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_VOTES).with(userHttpBasic(UserTestData.getNew(Role.USER))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void updateVoteOtherUser() throws Exception {
        Vote updated = new Vote(vote1);
        updated.setRestaurant(restaurant2);
        perform(MockMvcRequestBuilders.patch(URI.create(REST_URL_VOTES + updated.id()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(updated)))
                .with(userHttpBasic(user4)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    void createManyVoteByOneUser() throws Exception {
        Vote newVote = VoteTestData.getNew(restaurant2);
        perform(MockMvcRequestBuilders.post(URI.create(REST_URL_VOTES))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(newVote)))
                .with(userHttpBasic(user1)))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_VOTES + vote1.getId()).with(userHttpBasic(user2)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }
}