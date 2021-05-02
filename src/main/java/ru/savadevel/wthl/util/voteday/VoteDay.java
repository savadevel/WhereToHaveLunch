package ru.savadevel.wthl.util.voteday;

import java.time.Clock;
import java.time.LocalDate;

public interface VoteDay {
    LocalDate getNow();
    void setClock(Clock clock);
}
