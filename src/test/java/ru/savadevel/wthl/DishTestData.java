package ru.savadevel.wthl;

import ru.savadevel.wthl.model.Dish;

import java.math.BigDecimal;

import static ru.savadevel.wthl.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Dish.class);

    public static final int DISH1_ID = START_SEQ + 4;

    public static final Dish dish1 = new Dish(DISH1_ID, "first dish", BigDecimal.valueOf(0.53));
    public static final Dish dish2 = new Dish(DISH1_ID + 1, "second dish", BigDecimal.valueOf(5.03));

    public static Dish getNew() {
        return new Dish(null, "New dish", BigDecimal.valueOf(0.55));
    }
}
