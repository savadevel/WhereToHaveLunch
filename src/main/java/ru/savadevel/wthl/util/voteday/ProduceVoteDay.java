package ru.savadevel.wthl.util.voteday;

import java.time.Clock;
import java.time.LocalDate;

public class ProduceVoteDay implements VoteDay {
    private static final VoteDay voteDay = new ProduceVoteDay();
    private Clock clock = Clock.systemDefaultZone();

    private ProduceVoteDay() {
    }

    @Override
    public LocalDate getNow() {
        return LocalDate.now(clock);
    }

    @Override
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public static VoteDay getVoteDay() {
        return voteDay;
    }
}
