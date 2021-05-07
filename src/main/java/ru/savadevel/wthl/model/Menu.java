package ru.savadevel.wthl.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true, exclude = {"restaurant", "dish"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@NamedEntityGraph(name = Menu.MENU_RESTAURANT_AND_DISH,
        attributeNodes = {@NamedAttributeNode("restaurant"), @NamedAttributeNode("dish")})
@Table(name = "menus",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"restaurant_id", "dish_id", "on_date"}, name = "menus_unique_restaurant_dish_on_date_idx")},
        indexes = {@Index(columnList = "on_date", name = "menus_on_date_idx")})
public class Menu extends AbstractBaseEntity {
    public static final String MENU_RESTAURANT_AND_DISH = "Menu.restaurantAndDish";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dish_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Dish dish;

    @Column(name = "on_date", nullable = false)
    @NotNull
    private LocalDate date;

    public Menu(Integer id, Restaurant restaurant, Dish dish, LocalDate date) {
        super(id);
        this.restaurant = restaurant;
        this.dish = dish;
        this.date = date;
    }
}
