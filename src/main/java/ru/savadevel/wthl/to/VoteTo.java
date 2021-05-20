package ru.savadevel.wthl.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.savadevel.wthl.web.validation.VoteDayConstraint;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;

@Getter
@NoArgsConstructor
@VoteDayConstraint
public class VoteTo extends BaseTo {
    @Setter
    @NotNull
    private Integer restaurantId;

    private final LocalDate date = getVotingDay().getNowDate();

    public VoteTo(Integer id, Integer restaurantId) {
        super(id);
        this.restaurantId = restaurantId;
    }
}
