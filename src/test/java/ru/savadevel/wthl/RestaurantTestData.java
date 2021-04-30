package ru.savadevel.wthl;

import ru.savadevel.wthl.model.Restaurant;

import static ru.savadevel.wthl.model.AbstractBaseEntity.START_SEQ;

public class RestaurantTestData {
    public static TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int RESTAURANT1_ID = START_SEQ + 6;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "first restaurant");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "second restaurant");

    public static Restaurant getNew() {
        return new Restaurant(null, "New restaurant");
    }
}
