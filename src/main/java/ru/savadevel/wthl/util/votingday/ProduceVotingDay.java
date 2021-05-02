package ru.savadevel.wthl.util.votingday;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

public class ProduceVotingDay implements VotingDay {
    private static final VotingDay VOTING_DAY = new ProduceVotingDay();
    private Clock clock = Clock.systemDefaultZone();

    private ProduceVotingDay() {
    }

    @Override
    public LocalDate getNowDate() {
        return LocalDate.now(clock);
    }

    @Override
    public LocalTime getNowTime() {
        return LocalTime.now(clock);
    }

    @Override
    public void setClock(Clock clock) {
        this.clock = clock;
    }

    public static VotingDay getVotingDay() {
        return VOTING_DAY;
    }
}
