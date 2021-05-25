package ru.savadevel.wthl.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savadevel.wthl.UserTestData;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.model.Role;
import ru.savadevel.wthl.repository.MenuRepository;
import ru.savadevel.wthl.util.MenuUtil;

import java.net.URI;
import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savadevel.wthl.DishTestData.dish1;
import static ru.savadevel.wthl.MenuTestData.*;
import static ru.savadevel.wthl.RestaurantTestData.restaurant1;
import static ru.savadevel.wthl.TestUtil.userHttpBasic;
import static ru.savadevel.wthl.UserTestData.admin;
import static ru.savadevel.wthl.UserTestData.user1;

class MenuRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL_MENUS = MenuRestController.REST_URL + "/";

    @Autowired
    private MenuRepository menuRepository;

    @Test
    void getMenusByUserRole() throws Exception {
        checkGet(URI.create(MenuRestController.REST_URL), user1, MENU_MATCHER, menu1, menu2, menu4, menu5);
    }

    @Test
    void getMenusByAdminRole() throws Exception {
        checkGet(URI.create(REST_URL_MENUS), admin, MENU_MATCHER, menu1, menu2, menu4, menu5);
    }

    @Test
    void getMenuById() throws Exception {
        checkGet(URI.create(REST_URL_MENUS + menu1.getId()),
                admin, MENU_MATCHER, menu1);
    }

    @Test
    void addMenu() throws Exception {
        checkPostTo(URI.create(REST_URL_MENUS), admin, MENU_TO_MATCHER,
                getNew(restaurant1, dish1), Menu.class, MenuUtil::asTo, (id) -> menuRepository.getById(id));
    }

    @Test
    void deleteMenu() throws Exception {
        checkDelete(URI.create(REST_URL_MENUS + menu1.getId()),
                admin, () -> WebUtil.delete(menu1.id(), menuRepository::delete));
    }

    @Test
    void addMenuForbidden() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL_MENUS).with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteMenuForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_MENUS + menu1.getId()).with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void deleteDishesForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL_MENUS + menu1.getId()).with(userHttpBasic(user1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void addDuplicateMenu() throws Exception {
        Menu duplicate = getNew(menu1.getRestaurant(), menu1.getDish());
        duplicate.setDate(LocalDate.now());
        menuRepository.save(duplicate);
        checkDuplicate(URI.create(REST_URL_MENUS), admin, MenuUtil.asTo(duplicate));
    }


    @Test
    void getMenusUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENUS).with(userHttpBasic(UserTestData.getNew(Role.USER))))
                .andExpect(status().isUnauthorized());
    }

}