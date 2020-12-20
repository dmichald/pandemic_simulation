package com.md.pandemic_simulation.web.validators;

import com.md.pandemic_simulation.data.model.Simulation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SmallerThanPopulationValidator implements ConstraintValidator<SmallerThanPopulation, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        final Simulation simulation = (Simulation) o;
        return simulation.getI() < simulation.getP();
    }
}
