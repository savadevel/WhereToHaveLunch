package ru.savadevel.wthl.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;
import ru.savadevel.wthl.DishTestData;
import ru.savadevel.wthl.MenuTestData;
import ru.savadevel.wthl.RestaurantTestData;
import ru.savadevel.wthl.UserTestData;
import ru.savadevel.wthl.model.Dish;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.model.Role;
import ru.savadevel.wthl.repository.DishRepository;
import ru.savadevel.wthl.repository.MenuRepository;
import ru.savadevel.wthl.repository.RestaurantRepository;
import ru.savadevel.wthl.util.MenuUtil;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savadevel.wthl.DishTestData.*;
import static ru.savadevel.wthl.MenuTestData.*;
import static ru.savadevel.wthl.RestaurantTestData.*;
import static ru.savadevel.wthl.TestUtil.userHttpBasic;
import static ru.savadevel.wthl.UserTestData.admin;
import static ru.savadevel.wthl.UserTestData.user1;
import static ru.savadevel.wthl.web.WebUtil.*;

class AdminControllerTest extends AbstractControllerTest {
    private static final String REST_URL_DISHES = AdminController.REST_URL + PART_REST_URL_DISHES + "/";
    private static final String REST_URL_RESTAURANTS = AdminController.REST_URL + PART_REST_URL_RESTAURANTS + "/";
    private static final String REST_URL_MENUS = AdminController.REST_URL + PART_REST_URL_MENUS + "/";

    @Autowired
    private DishRepository dishRepository;
    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private MenuRepository menuRepository;

    @Test
    void getDishesAll() throws Exception {
        checkGet(URI.create(REST_URL_DISHES), admin, DISH_MATCHER, dish1, dish2);
    }

    @Test
    void getRestaurantsAll() throws Exception {
        checkGet(URI.create(REST_URL_RESTAURANTS), admin, RESTAURANT_MATCHER, restaurant1, restaurant2);
    }

    @Test
    void getMenusAll() throws Exception {
        checkGet(URI.create(REST_URL_RESTAURANTS + restaurant1.getId() + PART_REST_URL_MENUS), admin, MENU_MATCHER,
                menu1, menu2, menu3);
    }

    @Test
    void getMenuRestaurantByDate() throws Exception {
        URI uri = UriComponentsBuilder
                .fromUriString(REST_URL_RESTAURANTS + restaurant1.getId() + PART_REST_URL_MENUS + "/?date={date}")
                .buildAndExpand(menu1.getDate())
                .encode()
                .toUri();
        checkGet(uri, admin, MENU_MATCHER, menu1, menu2);
    }

    @Test
    void getDishById() throws Exception {
        checkGet(URI.create(REST_URL_DISHES + dish1.getId()),
                admin, DISH_MATCHER, dish1);
    }

    @Test
    void getRestaurantById() throws Exception {
        checkGet(URI.create(REST_URL_RESTAURANTS + restaurant1.getId()),
                admin, RESTAURANT_MATCHER, restaurant1);
    }

    @Test
    void getMenuByIs() throws Exception {
        checkGet(URI.create(REST_URL_MENUS + menu1.getId()),
                admin, MENU_MATCHER, menu1);
    }

    @Test
    void addDish() throws Exception {
        checkPost(URI.create(REST_URL_DISHES), admin, DISH_MATCHER,
                DishTestData.getNew(), Dish.class, (id) -> dishRepository.getById(id));
    }

    @Test
    void addRestaurant() throws Exception {
        checkPost(URI.create(REST_URL_RESTAURANTS), admin, RESTAURANT_MATCHER,
                RestaurantTestData.getNew(), Restaurant.class, (id) -> restaurantRepository.getById(id));
    }

    @Test
    void addMenu() throws Exception {
        checkPostTo(URI.create(REST_URL_MENUS), admin, MENU_TO_MATCHER,
                MenuTestData.getNew(restaurant1, dish1), Menu.class, MenuUtil::asTo, (id) -> menuRepository.getById(id));
    }

    @Test
    void getDishesForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_DISHES).with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getDishesUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_DISHES).with(userHttpBasic(UserTestData.getNew(Role.ADMIN))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getRestaurantsForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANTS).with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getRestaurantsUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANTS).with(userHttpBasic(UserTestData.getNew(Role.ADMIN))))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getMenusForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENUS).with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getMenusUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENUS).with(userHttpBasic(UserTestData.getNew(Role.ADMIN))))
                .andExpect(status().isUnauthorized());
    }
}