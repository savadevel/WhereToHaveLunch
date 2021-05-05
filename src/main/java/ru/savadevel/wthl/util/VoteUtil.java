package ru.savadevel.wthl.util;

import ru.savadevel.wthl.model.Restaurant;
import ru.savadevel.wthl.model.Role;
import ru.savadevel.wthl.model.User;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.to.VoteTo;
import ru.savadevel.wthl.web.SecurityUtil;

public class VoteUtil {
    private VoteUtil() {
    }

    public static Vote createNewFromTo(VoteTo voteTo) {
        return new Vote(null, getUser(), getRestaurant(voteTo), voteTo.getDate());
    }

    public static Vote updateFromTo(Vote vote, VoteTo voteTo) {
        vote.setRestaurant(new Restaurant(voteTo.getRestaurantId(), null));
        vote.setDate(voteTo.getDate());
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
        return new User(SecurityUtil.get().getUsername(), null, Role.USER);
    }
}
