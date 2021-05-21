package ru.savadevel.wthl.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.savadevel.wthl.web.validation.VoteDayConstraint;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@VoteDayConstraint
public class VoteTo extends BaseTo {
    @NotNull
    private Integer restaurantId;

    public VoteTo(Integer id, Integer restaurantId) {
        super(id);
        this.restaurantId = restaurantId;
    }
}
