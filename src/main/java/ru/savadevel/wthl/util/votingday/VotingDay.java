package ru.savadevel.wthl.util.votingday;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

public interface VotingDay {
    LocalDate getNowDate();
    LocalTime getNowTime();
    void setClock(Clock clock);
}
