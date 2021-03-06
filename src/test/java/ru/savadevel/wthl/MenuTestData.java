package ru.savadevel.wthl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.savadevel.wthl.model.Dish;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.to.MenuTo;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.time.LocalDate.of;
import static ru.savadevel.wthl.DishTestData.dish1;
import static ru.savadevel.wthl.DishTestData.dish2;
import static ru.savadevel.wthl.RestaurantTestData.restaurant1;
import static ru.savadevel.wthl.RestaurantTestData.restaurant2;
import static ru.savadevel.wthl.model.AbstractBaseEntity.START_SEQ;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuTestData {
    public static TestMatcher<Menu> MENU_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Menu.class);
    public static TestMatcher<MenuTo> MENU_TO_MATCHER = TestMatcher.usingIgnoringFieldsComparator(MenuTo.class);

    public static final int MENU1_ID = START_SEQ + 9;

    public static final Menu menu1 = new Menu(MENU1_ID, restaurant1, dish1, of(2021, 1, 1), BigDecimal.valueOf(0.53));
    public static final Menu menu2 = new Menu(MENU1_ID + 1, restaurant1, dish2, of(2021, 1, 1), BigDecimal.valueOf(5.03));
    public static final Menu menu3 = new Menu(MENU1_ID + 2, restaurant1, dish2, of(2021, 1, 2), BigDecimal.valueOf(5.03));
    public static final Menu menu4 = new Menu(MENU1_ID + 3, restaurant2, dish1, of(2021, 1, 1), BigDecimal.valueOf(0.53));
    public static final Menu menu5 = new Menu(MENU1_ID + 4, restaurant2, dish2, of(2021, 1, 1), BigDecimal.valueOf(5.03));

    public static Menu getNew(Restaurant restaurant, Dish dish) {
        return new Menu(null, restaurant, dish, LocalDate.now(), BigDecimal.valueOf(0.55));
    }
}
