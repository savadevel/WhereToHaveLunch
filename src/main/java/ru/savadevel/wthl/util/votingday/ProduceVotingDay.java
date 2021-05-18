package ru.savadevel.wthl.util.votingday;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProduceVotingDay implements VotingDay {
    private static final VotingDay votingDay = new ProduceVotingDay();

    private Clock clock = Clock.systemDefaultZone();

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
        return votingDay;
    }
}
