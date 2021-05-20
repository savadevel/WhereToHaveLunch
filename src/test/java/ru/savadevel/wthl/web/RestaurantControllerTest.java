package ru.savadevel.wthl.web;

import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.savadevel.wthl.UserTestData;
import ru.savadevel.wthl.model.Role;

import java.net.URI;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.savadevel.wthl.MenuTestData.*;
import static ru.savadevel.wthl.TestUtil.userHttpBasic;
import static ru.savadevel.wthl.UserTestData.admin;
import static ru.savadevel.wthl.UserTestData.user1;
import static ru.savadevel.wthl.web.WebUtil.PART_REST_URL_MENUS;

class RestaurantControllerTest extends AbstractControllerTest {
    private static final String REST_URL_MENUS = RestaurantController.REST_URL + PART_REST_URL_MENUS;

    @Test
    void getMenusByUserRole() throws Exception {
        checkGet(URI.create(REST_URL_MENUS), user1, MENU_MATCHER, menu1, menu2, menu4, menu5);
    }

    @Test
    void getMenusByAdminRole() throws Exception {
        checkGet(URI.create(REST_URL_MENUS), admin, MENU_MATCHER, menu1, menu2, menu4, menu5);
    }

    @Test
    void getMenusUnauthorized() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL_MENUS).with(userHttpBasic(UserTestData.getNew(Role.USER))))
                .andExpect(status().isUnauthorized());
    }
}