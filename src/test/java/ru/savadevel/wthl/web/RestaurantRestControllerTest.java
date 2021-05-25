package ru.savadevel.wthl.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savadevel.wthl.DishTestData;
import ru.savadevel.wthl.RestaurantTestData;
import ru.savadevel.wthl.UserTestData;
import ru.savadevel.wthl.model.Dish;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.model.Role;
import ru.savadevel.wthl.repository.DishRepository;
import ru.savadevel.wthl.repository.RestaurantRepository;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savadevel.wthl.DishTestData.*;
import static ru.savadevel.wthl.RestaurantTestData.*;
import static ru.savadevel.wthl.TestUtil.userHttpBasic;
import static ru.savadevel.wthl.UserTestData.admin;
import static ru.savadevel.wthl.UserTestData.user1;

class RestaurantRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_RESTAURANTS = RestaurantRestController.REST_URL + "/";

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    void getRestaurantsAll() throws Exception {
        checkGet(URI.create(REST_URL_RESTAURANTS), admin, RESTAURANT_MATCHER, restaurant1, restaurant2);
    }

    @Test
    void getRestaurantById() throws Exception {
        checkGet(URI.create(REST_URL_RESTAURANTS + restaurant1.getId()),
                admin, RESTAURANT_MATCHER, restaurant1);
    }

    @Test
    void addRestaurant() throws Exception {
        checkPost(URI.create(REST_URL_RESTAURANTS), admin, RESTAURANT_MATCHER,
                RestaurantTestData.getNew(), Restaurant.class, (id) -> restaurantRepository.getById(id));
    }

    @Test
    void addDuplicateRestaurant() throws Exception {
        Restaurant duplicate = RestaurantTestData.getNew();
        duplicate.setName(restaurant1.getName());
        checkDuplicate(URI.create(REST_URL_RESTAURANTS), admin, duplicate);
    }
    @Test
    void deleteRestaurant() throws Exception {
        checkDelete(URI.create(REST_URL_RESTAURANTS + restaurant1.getId()),
                admin, () -> WebUtil.delete(restaurant1.id(), restaurantRepository::delete));
    }

    @Test
    void getRestaurantsForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANTS).with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void addRestaurantForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANTS).with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteRestaurantForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_RESTAURANTS + restaurant1.getId()).with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getRestaurantsUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANTS).with(userHttpBasic(UserTestData.getNew(Role.ADMIN))))
                .andExpect(status().isUnauthorized());
    }
}