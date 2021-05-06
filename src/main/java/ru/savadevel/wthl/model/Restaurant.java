package ru.savadevel.wthl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@ToString(callSuper = true)
@NoArgsConstructor
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @Getter
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<Menu> menus;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
