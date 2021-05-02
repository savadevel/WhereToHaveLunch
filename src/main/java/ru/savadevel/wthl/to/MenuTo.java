package ru.savadevel.wthl.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuTo extends BaseTo {
    private Integer restaurantId;
    private Integer dishId;

    public MenuTo(Integer id, Integer restaurantId, Integer dishId) {
        super(id);
        this.restaurantId = restaurantId;
        this.dishId = dishId;
    }
}
