package ru.savadevel.wthl;

import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.to.VoteTo;

import static java.time.LocalDate.of;
import static ru.savadevel.wthl.RestaurantTestData.restaurant1;
import static ru.savadevel.wthl.RestaurantTestData.restaurant2;
import static ru.savadevel.wthl.UserTestData.*;
import static ru.savadevel.wthl.model.AbstractBaseEntity.START_SEQ;
import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant.menus");
    public static TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingIgnoringFieldsComparator(VoteTo.class);

    public static final int VOTE1_ID = START_SEQ + 11;

    public static final Vote vote1 = new Vote(VOTE1_ID, user1, restaurant1, of(2021, 1,1));
    public static final Vote vote2 = new Vote(VOTE1_ID + 1, user2, restaurant1, of(2021, 1,2));
    public static final Vote vote3 = new Vote(VOTE1_ID + 2, user3, restaurant1, of(2021, 1,2));
    public static final Vote vote4 = new Vote(VOTE1_ID + 3, user2, restaurant2, of(2021, 1,1));
    public static final Vote vote5 = new Vote(VOTE1_ID + 4, user3, restaurant2, of(2021, 1,1));
    public static final Vote vote6 = new Vote(VOTE1_ID + 5, user1, restaurant2, of(2021, 1,2));
    public static final Vote vote7 = new Vote(VOTE1_ID + 6, user1, restaurant2, of(2021, 1,3));
    public static final Vote vote8 = new Vote(VOTE1_ID + 7, user2, restaurant2, of(2021, 1,3));
    public static final Vote vote9 = new Vote(VOTE1_ID + 8, user3, restaurant2, of(2021, 1,3));

    public static Vote getNew(Restaurant restaurant) {
        return new Vote(null, null, restaurant, getVotingDay().getNowDate());
    }
}
