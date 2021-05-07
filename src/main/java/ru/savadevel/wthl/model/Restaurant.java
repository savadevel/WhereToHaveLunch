package ru.savadevel.wthl.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@ToString(callSuper = true, exclude = {"menus", "votes"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "restaurants", uniqueConstraints = {@UniqueConstraint(columnNames = "name", name = "restaurants_unique_name_idx")})
public class Restaurant extends AbstractNamedEntity {

    @Getter
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonBackReference("restaurant<menu")
    private List<Menu> menus;

    @Getter
    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    @JsonBackReference("restaurant<vote")
    private List<Vote> votes;

    public Restaurant(Integer id, String name) {
        super(id, name);
    }
}
