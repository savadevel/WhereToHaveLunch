package ru.savadevel.wthl.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static ru.savadevel.wthl.util.voteday.ProduceVoteDay.getVoteDay;

@Getter
@NoArgsConstructor
public class VoteTo extends BaseTo {
    @Setter
    private Integer restaurantId;
    private final LocalDate date = getVoteDay().getNow();

    public VoteTo(Integer id, Integer restaurantId) {
        super(id);
        this.restaurantId = restaurantId;
    }
}
