package ru.savadevel.wthl.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savadevel.wthl.DishTestData;
import ru.savadevel.wthl.MenuTestData;
import ru.savadevel.wthl.RestaurantTestData;
import ru.savadevel.wthl.model.Dish;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.repository.DishRepository;
import ru.savadevel.wthl.repository.MenuRepository;
import ru.savadevel.wthl.repository.RestaurantRepository;
import ru.savadevel.wthl.web.json.JsonUtil;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savadevel.wthl.DishTestData.*;
import static ru.savadevel.wthl.MenuTestData.*;
import static ru.savadevel.wthl.RestaurantTestData.*;
import static ru.savadevel.wthl.TestUtil.readFromJson;
import static ru.savadevel.wthl.util.MenuUtils.asTo;
import static ru.savadevel.wthl.web.WebUtils.*;

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

    // TODO extract common to utils
    @Test
    void getDishesAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_DISHES))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1, dish2));
    }

    @Test
    void getRestaurantsAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANTS))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1, restaurant2));
    }

    @Test
    void getMenusAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANTS + "/" + restaurant1.getId() + PART_REST_URL_MENUS))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1, menu2, menu3));
    }

    @Test
    void getMenuRestaurantByDate() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANTS + "/" + restaurant1.getId() + PART_REST_URL_MENUS)
                .param("date", menu1.getDate().toString()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1, menu2));
    }

    @Test
    void getDishById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_DISHES + dish1.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_MATCHER.contentJson(dish1));
    }

    @Test
    void getRestaurantById() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_RESTAURANTS + restaurant1.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1));
    }

    @Test
    void getMenuByIs() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENUS + menu1.getId()))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MENU_MATCHER.contentJson(menu1));
    }

    @Test
    void addDish() throws Exception {
        Dish newDish = DishTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_DISHES)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Dish created = readFromJson(action, Dish.class);
        newDish.setId(created.id());
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.findById(created.id()).orElse(null), newDish);
    }

    @Test
    void addRestaurant() throws Exception {
        Restaurant newRestaurant = RestaurantTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_RESTAURANTS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Restaurant created = readFromJson(action, Restaurant.class);
        newRestaurant.setId(created.id());
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.findById(created.id()).orElse(null), newRestaurant);
    }

    @Test
    void addMenu() throws Exception {
        Menu newMenu = MenuTestData.getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL_MENUS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(asTo(newMenu))))
                .andExpect(status().isCreated())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));

        Menu created = readFromJson(action, Menu.class);
        newMenu.setId(created.id());
        MENU_MATCHER.assertMatch(created, newMenu);
        MENU_MATCHER.assertMatch(menuRepository.findById(created.id()).orElse(null), newMenu);
    }
}