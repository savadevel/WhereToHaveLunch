package ru.savadevel.wthl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

@ToString(callSuper = true, exclude = "menus")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "dishes_unique_name_idx")})
public class Dish extends AbstractNamedEntity {

    @Getter
    @Setter
    @Column(name = "price", nullable = false, precision = 20, scale = 2)
    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("10000.00")
    private BigDecimal price;

    @Getter
    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY)
    @JsonBackReference("dish<menu")
    private List<Menu> menus;

    public Dish(Integer id, String name, BigDecimal price) {
        super(id, name);
        this.price = price;
    }
}
