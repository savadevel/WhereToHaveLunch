package ru.savadevel.wthl;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.savadevel.wthl.model.Dish;

import static ru.savadevel.wthl.model.AbstractBaseEntity.START_SEQ;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DishTestData {
    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Dish.class, "menus");

    public static final int DISH1_ID = START_SEQ + 5;

    public static final Dish dish1 = new Dish(DISH1_ID, "first dish");
    public static final Dish dish2 = new Dish(DISH1_ID + 1, "second dish");

    public static Dish getNew() {
        return new Dish(null, "New dish");
    }
}
