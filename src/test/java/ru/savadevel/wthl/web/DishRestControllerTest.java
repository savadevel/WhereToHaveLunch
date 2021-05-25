package ru.savadevel.wthl.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savadevel.wthl.UserTestData;
import ru.savadevel.wthl.model.Dish;
import ru.savadevel.wthl.model.Role;
import ru.savadevel.wthl.repository.DishRepository;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savadevel.wthl.DishTestData.*;
import static ru.savadevel.wthl.TestUtil.userHttpBasic;
import static ru.savadevel.wthl.UserTestData.admin;
import static ru.savadevel.wthl.UserTestData.user1;

class DishRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_DISHES = DishRestController.REST_URL + "/";

    @Autowired
    private DishRepository dishRepository;

    @Test
    void getDishesAll() throws Exception {
        checkGet(URI.create(REST_URL_DISHES), admin, DISH_MATCHER, dish1, dish2);
    }

    @Test
    void getDishById() throws Exception {
        checkGet(URI.create(REST_URL_DISHES + dish1.getId()),
                admin, DISH_MATCHER, dish1);
    }

    @Test
    void addDish() throws Exception {
        checkPost(URI.create(REST_URL_DISHES), admin, DISH_MATCHER,
                getNew(), Dish.class, (id) -> dishRepository.getById(id));
    }

    @Test
    void addDuplicateDish() throws Exception {
        Dish duplicate = getNew();
        duplicate.setName(dish1.getName());
        checkDuplicate(URI.create(REST_URL_DISHES), admin, duplicate);
    }

    @Test
    void deleteDish() throws Exception {
        checkDelete(URI.create(REST_URL_DISHES + dish1.getId()),
                admin, () -> WebUtil.delete(dish1.id(), dishRepository::delete));
    }

    @Test
    void getDishesForbidden() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_DISHES).with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void addDishForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_DISHES).with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void getDishesUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_DISHES).with(userHttpBasic(UserTestData.getNew(Role.ADMIN))))
                .andExpect(status().isUnauthorized());
    }
}