package com.md.pandemic_simulation.web.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = SmallerThanPopulationValidator.class)
@Documented
public @interface InfectedLessThanPopulation {
    String message() default "Initial number of infected people must be smaller than population";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
