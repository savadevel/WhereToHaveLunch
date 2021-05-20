package ru.savadevel.wthl.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class MenuTo extends BaseTo {
    @NotNull
    private Integer restaurantId;
    @NotNull
    private Integer dishId;
    @NotNull
    private BigDecimal price;

    public MenuTo(Integer id, Integer restaurantId, Integer dishId, BigDecimal price) {
        super(id);
        this.restaurantId = restaurantId;
        this.dishId = dishId;
        this.price = price;
    }
}
