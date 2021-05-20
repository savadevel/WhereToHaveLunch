package ru.savadevel.wthl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@ToString(callSuper = true, exclude = "menus")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "dishes", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "dishes_unique_name_idx")})
public class Dish extends AbstractNamedEntity {

    @OneToMany(mappedBy = "dish", fetch = FetchType.LAZY)
    @JsonBackReference("dish<menu")
    private List<Menu> menus;

    public Dish(Integer id, String name) {
        super(id, name);
    }
}
