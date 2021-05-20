package ru.savadevel.wthl.to;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

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
    @NotNull
    @FutureOrPresent
    private LocalDate date;

    public MenuTo(Integer id, Integer restaurantId, Integer dishId, LocalDate date, BigDecimal price) {
        super(id);
        this.restaurantId = restaurantId;
        this.dishId = dishId;
        this.date = date;
        this.price = price;
    }
}
