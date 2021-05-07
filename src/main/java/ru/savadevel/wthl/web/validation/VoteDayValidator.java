package ru.savadevel.wthl.web.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.LocalTime;

import static ru.savadevel.wthl.util.votingday.ProduceVotingDay.getVotingDay;

public class VoteDayValidator implements ConstraintValidator<VoteDayConstraint, LocalDate> {
    private static final LocalTime FINISH_VOTE_TIME = LocalTime.of(10, 59, 59);
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        return !getVotingDay().getNowTime().isAfter(FINISH_VOTE_TIME);
    }
}
