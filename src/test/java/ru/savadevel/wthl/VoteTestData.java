package ru.savadevel.wthl;

import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.to.VoteTo;

import static java.time.LocalDate.of;
import static ru.savadevel.wthl.RestaurantTestData.restaurant1;
import static ru.savadevel.wthl.UserTestData.user1;
import static ru.savadevel.wthl.model.AbstractBaseEntity.START_SEQ;
import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;

public class VoteTestData {
    public static TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "user", "restaurant.menus", "restaurant.votes");
    public static TestMatcher<VoteTo> VOTE_TO_MATCHER = TestMatcher.usingIgnoringFieldsComparator(VoteTo.class);

    public static final int VOTE1_ID = START_SEQ + 11;

    public static final Vote vote1 = new Vote(VOTE1_ID, user1, restaurant1, of(2021, 1,1));

    public static Vote getNew(Restaurant restaurant) {
        return new Vote(null, null, restaurant, getVotingDay().getNowDate());
    }
}
