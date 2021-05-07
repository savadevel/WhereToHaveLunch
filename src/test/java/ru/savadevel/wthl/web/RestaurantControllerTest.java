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
import static ru.savadevel.wthl.MenuTestData.*;
import static ru.savadevel.wthl.RestaurantTestData.restaurant1;
import static ru.savadevel.wthl.RestaurantTestData.restaurant2;
import static ru.savadevel.wthl.TestUtil.userHttpBasic;
import static ru.savadevel.wthl.UserTestData.*;
import static ru.savadevel.wthl.VoteTestData.*;
import static ru.savadevel.wthl.VotesTestData.*;
import static ru.savadevel.wthl.util.VoteUtil.asTo;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_MENUS;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_VOTES;

class RestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL_MENUS = RestaurantController.REST_URL + PART_REST_URL_MENUS + "/";
    private static final String REST_URL_VOTES = RestaurantController.REST_URL + PART_REST_URL_VOTES + "/";
    private final static LocalDateTime VOTE_TIME_INVALID = LocalDateTime.of(2021, 1, 1, 11, 0, 0, 0);

    @Autowired
    private VoteRepository repository;

    @Test
    void getMenusOnCurrentDate() throws Exception {
        checkGet(URI.create(REST_URL_MENUS), user1, MENU_MATCHER, menu1, menu2, menu4, menu5);
    }

    @Test
    void getAmountVotesForRestaurantsOnCurrentDate() throws Exception {
        checkGet(URI.create(REST_URL_VOTES), user1, VOTES_TO_MATCHER, votesTo2, votesTo1);
    }

    @Test
    void getVoteById() throws Exception {
        checkGet(URI.create(REST_URL_VOTES + vote1.getId()), user1, VOTE_MATCHER, vote1);
    }

    @Test
    void createVote() throws Exception {
        checkPostTo(URI.create(REST_URL_VOTES), user4, VOTE_TO_MATCHER, VoteTestData.getNew(restaurant1),
                Vote.class, VoteUtil::asTo, (id) -> repository.getVoteByIdAndUserUsername(id, user4.getUsername()));
    }

    @Test
    void createWithInvalidTimeForVote() throws Exception {
        setDateTime(VOTE_TIME_INVALID);
        perform(MockMvcRequestBuilders.post(URI.create(REST_URL_VOTES))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(VoteTestData.getNew(restaurant1))))
                .with(userHttpBasic(user1)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void updateVote() throws Exception {
        Vote updated = new Vote(vote1);
        updated.setRestaurant(restaurant2);
        perform(MockMvcRequestBuilders.patch(URI.create(REST_URL_VOTES + updated.id()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(updated)))
                .with(userHttpBasic(user1)))
                .andExpect(status().isNoContent())
                .andDo(print());

        VOTE_MATCHER.assertMatch(repository.getVoteByIdAndUserUsername(updated.id(), user1.getUsername()), updated);
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
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void getVotesForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_VOTES).with(userHttpBasic(admin)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getVotesUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_VOTES).with(userHttpBasic(UserTestData.getNew(Role.USER))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getMenusForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENUS).with(userHttpBasic(admin)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getMenusUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENUS).with(userHttpBasic(UserTestData.getNew(Role.USER))))
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
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }

    @Test
    void createManyVoteByOneUser() throws Exception {
        Vote newVote = VoteTestData.getNew(restaurant2);
        perform(MockMvcRequestBuilders.post(URI.create(REST_URL_VOTES))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(newVote)))
                .with(userHttpBasic(user1)))
                .andExpect(status().isConflict())
                .andDo(print());
    }

    @Test
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_VOTES + vote1.getId()).with(userHttpBasic(user2)))
                .andExpect(status().isUnprocessableEntity())
                .andDo(print());
    }
}