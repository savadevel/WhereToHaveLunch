package ru.savadevel.wthl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.model.Votes;

import java.time.LocalDate;

import static ru.savadevel.wthl.RestaurantTestData.restaurant1;
import static ru.savadevel.wthl.RestaurantTestData.restaurant2;
import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;

public class VotesTestData {
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VotesTo implements Votes {
        private LocalDate date;
        private Restaurant restaurant;
        private Integer votes;
    }

    public static TestMatcher<VotesTo> VOTES_TO_MATCHER = TestMatcher.usingIgnoringFieldsComparator(VotesTo.class);

    public static final VotesTo votesTo1 = new VotesTo(getVotingDay().getNowDate(), restaurant1, 1);
    public static final VotesTo votesTo2 = new VotesTo(getVotingDay().getNowDate(), restaurant2, 2);
}
