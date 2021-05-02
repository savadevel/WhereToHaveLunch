package ru.savadevel.wthl.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MenuTo extends BaseTo {
    @NotNull
    private Integer restaurantId;
    @NotNull
    private Integer dishId;

    public MenuTo(Integer id, Integer restaurantId, Integer dishId) {
        super(id);
        this.restaurantId = restaurantId;
        this.dishId = dishId;
    }
}
