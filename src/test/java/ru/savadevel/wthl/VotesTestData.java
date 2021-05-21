package ru.savadevel.wthl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.model.VoteResult;

import java.time.LocalDate;

import static ru.savadevel.wthl.RestaurantTestData.restaurant1;
import static ru.savadevel.wthl.RestaurantTestData.restaurant2;
import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;

public class VotesTestData {
    public static TestMatcher<VoteResultTo> VOTES_TO_MATCHER = TestMatcher.usingIgnoringFieldsComparator(VoteResultTo.class);
    public static final VoteResultTo votesTo1 = new VoteResultTo(getVotingDay().getNowDate(), restaurant1, 1);
    public static final VoteResultTo votesTo2 = new VoteResultTo(getVotingDay().getNowDate(), restaurant2, 2);

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class VoteResultTo implements VoteResult {
        private LocalDate date;
        private Restaurant restaurant;
        private Integer votes;
    }
}
