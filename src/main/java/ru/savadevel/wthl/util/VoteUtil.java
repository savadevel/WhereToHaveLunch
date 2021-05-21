package ru.savadevel.wthl.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.model.User;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.security.SecurityUtil;
import ru.savadevel.wthl.to.VoteTo;

import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class VoteUtil {
    public static Vote createNewFromTo(VoteTo voteTo) {
        return new Vote(null, getUser(), getRestaurant(voteTo), getVotingDay().getNowDate());
    }

    public static Vote updateFromTo(Vote vote, VoteTo voteTo) {
        vote.setRestaurant(new Restaurant(voteTo.getRestaurantId(), null));
        vote.setDate(getVotingDay().getNowDate());
        vote.setUser(getUser());
        return vote;
    }

    public static VoteTo asTo(Vote vote) {
        return new VoteTo(vote.getId(), vote.getRestaurant().id());
    }

    private static Restaurant getRestaurant(VoteTo voteTo) {
        return new Restaurant(voteTo.getRestaurantId(), null);
    }

    private static User getUser() {
        return SecurityUtil.get().getUser();
    }
}
