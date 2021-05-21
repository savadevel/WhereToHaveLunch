package ru.savadevel.wthl.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@ToString(callSuper = true, exclude = {"user", "restaurant"})
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@NamedEntityGraph(name = Vote.VOTE_RESTAURANT,
        attributeNodes = @NamedAttributeNode("restaurant"))
@Table(name = "votes",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "on_date"}, name = "votes_unique_restaurant_user_on_date_idx")},
        indexes = {@Index(columnList = "on_date", name = "votes_on_date_idx")})
@DynamicUpdate
public class Vote extends AbstractBaseEntity {
    public static final String VOTE_RESTAURANT = "Vote.restaurant";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @Column(name = "on_date", nullable = false)
    @NotNull
    private LocalDate date;

    public Vote(Integer id, User user, Restaurant restaurant, LocalDate date) {
        super(id);
        this.user = user;
        this.restaurant = restaurant;
        this.date = date;
    }

    public Vote(Vote vote) {
        super(vote.id);
        this.user = vote.user;
        this.restaurant = vote.restaurant;
        this.date = vote.date;
    }
}
