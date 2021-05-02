package ru.savadevel.wthl.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savadevel.wthl.VoteTestData;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.repository.VoteRepository;
import ru.savadevel.wthl.util.VoteUtil;
import ru.savadevel.wthl.web.json.JsonUtil;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savadevel.wthl.MenuTestData.*;
import static ru.savadevel.wthl.RestaurantTestData.restaurant2;
import static ru.savadevel.wthl.VoteTestData.*;
import static ru.savadevel.wthl.VotesTestData.*;
import static ru.savadevel.wthl.util.VoteUtil.asTo;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_MENUS;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_VOTES;

class RestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL_MENUS = RestaurantController.REST_URL + PART_REST_URL_MENUS + "/";
    private static final String REST_URL_VOTES = RestaurantController.REST_URL + PART_REST_URL_VOTES + "/";

    @Autowired
    private VoteRepository repository;

    @Test
    void getMenusOnCurrentDate() throws Exception {
        checkGet(URI.create(REST_URL_MENUS), MENU_MATCHER, menu1, menu2, menu4, menu5);
    }

    @Test
    void getAmountVotesForRestaurantsOnCurrentDate() throws Exception {
        checkGet(URI.create(REST_URL_VOTES), VOTES_TO_MATCHER, votesTo2, votesTo1);
    }

    @Test
    void getVoteById() throws Exception {
        checkGet(URI.create(REST_URL_VOTES + vote1.getId()), VOTE_MATCHER, vote1);
    }

    @Test
    void createVote() throws Exception {
        checkPostTo(URI.create(REST_URL_VOTES), VOTE_TO_MATCHER, VoteTestData.getNew(), Vote.class, VoteUtil::asTo, repository);
    }

    @Test
    void updateVote() throws Exception {
        Vote updated = repository.save(VoteTestData.getNew());
        updated.setRestaurant(restaurant2);
        perform(MockMvcRequestBuilders.patch(URI.create(REST_URL_VOTES + updated.id()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(updated))))
                .andExpect(status().isNoContent())
                .andDo(print());

        VOTE_MATCHER.assertMatch(repository.findById(updated.id()).orElse(null), updated);
    }

    // TODO add test for invalid vote
}