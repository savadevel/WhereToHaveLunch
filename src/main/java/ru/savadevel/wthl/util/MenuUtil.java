package ru.savadevel.wthl.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.savadevel.wthl.model.Dish;
import ru.savadevel.wthl.model.Menu;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.to.MenuTo;

import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MenuUtil {
    public static Menu createNewFromTo(MenuTo menuTo) {
        return new Menu(null, new Restaurant(menuTo.getRestaurantId(), null), new Dish(menuTo.getDishId(), null, null), LocalDate.now());
    }

    public static MenuTo asTo(Menu menu) {
        return new MenuTo(menu.getId(), menu.getRestaurant().id(), menu.getDish().id());
    }
}
