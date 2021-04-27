package ru.savadevel.wthl.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

// TODO check the generation of DDL (the fact that after changing the Entity, the correct DDL is formed, corresponds to the script)
// TODO IDEA doesn't see all TODOs, like the bottom one when it's alone
// TODO specify indices, constrains so that can be created from java
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "menus", uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "dish_id", "on_date"}, name = "menus_unique_restaurant_dish_on_date_idx")})
public class Menu extends AbstractBaseEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private Dish dish;

    @Column(name = "on_date", nullable = false)
    @NotNull
    private LocalDate date;
}
