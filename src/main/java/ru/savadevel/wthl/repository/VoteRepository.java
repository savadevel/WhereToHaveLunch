package ru.savadevel.wthl.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.savadevel.wthl.model.Vote;
import ru.savadevel.wthl.model.Votes;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static ru.savadevel.wthl.model.Vote.VOTE_RESTAURANT;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends JpaRepository<Vote, Integer> {
    @Query("select v.date as date, v.restaurant as restaurant,  count(v.id) as votes " +
            "from Vote v " +
            "where v.date=:date " +
            "group by v.date, v.restaurant.id, v.restaurant.name " +
            "order by count(v.id) desc, v.restaurant.name")
    List<Votes> getAmount(@Param("date") LocalDate date);

    @EntityGraph(VOTE_RESTAURANT)
    Vote getVoteByUserUsernameAndDate(@NotBlank @Size(min = 3, max = 128) String username, @NotNull LocalDate date);

    @EntityGraph(VOTE_RESTAURANT)
    Vote getVoteByIdAndUserUsername(Integer id, @NotBlank @Size(min = 3, max = 128) String username);
}
