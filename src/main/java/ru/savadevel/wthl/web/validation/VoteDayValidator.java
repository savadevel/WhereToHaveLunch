package ru.savadevel.wthl.web.validation;

import ru.savadevel.wthl.to.VoteTo;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalTime;

import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;

public class VoteDayValidator implements ConstraintValidator<VoteDayConstraint, VoteTo> {
    private static final LocalTime FINISH_CHANGE_VOTE_TIME = LocalTime.of(11, 0, 0);

    @Override
    public boolean isValid(VoteTo value, ConstraintValidatorContext context) {
        if (value.isNew())
            return true;
        return getVotingDay().getNowTime().isBefore(FINISH_CHANGE_VOTE_TIME);
    }
}
