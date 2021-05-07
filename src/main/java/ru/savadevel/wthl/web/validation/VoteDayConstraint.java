package ru.savadevel.wthl.web.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy=VoteDayValidator.class)
public @interface VoteDayConstraint {
    String message() default "Vote is after 11:00, it's too late";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
