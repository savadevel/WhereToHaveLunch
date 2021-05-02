package ru.savadevel.wthl.to;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseTo {
    protected Integer id;

    public boolean isNew() {
        return getId() == null;
    }

    public int id() {
        Assert.notNull(getId(), "Entity must has id");
        return getId();
    }
}
