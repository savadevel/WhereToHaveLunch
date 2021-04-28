package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.model.Votes;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("select v.date as date, v.restaurant as restaurant,  count(v.id) as votes " +
            "from Vote v " +
            "where v.date=:date or :date is null " +
            "group by v.date, v.restaurant.id, v.restaurant.name " +
            "order by v.date desc, count(v.id) desc, v.restaurant.name")
    List<Votes> getAmountVotesForRestaurants(@Param("date") LocalDate date);
}
