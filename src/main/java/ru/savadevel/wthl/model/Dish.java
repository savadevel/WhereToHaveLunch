package ru.savadevel.wthl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "dishes_unique_name_idx")})
public class Dish extends AbstractNamedEntity { // TODO super class constructor is not called, in AllArgsConstructor does not pass id and name for AbstractNamedEntity

    @Column(name = "price", nullable = false, precision = 20, scale = 2)
    @NotNull
    @DecimalMin("0.01")
    @DecimalMax("10000.00")
    private BigDecimal price;

    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Menu> menus;
}
