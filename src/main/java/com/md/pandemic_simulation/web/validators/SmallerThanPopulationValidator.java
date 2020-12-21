package com.md.pandemic_simulation.web.validators;

import com.md.pandemic_simulation.web.dto.CreateSimulationDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SmallerThanPopulationValidator implements ConstraintValidator<InfectedLessThanPopulation, Object> {

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        final CreateSimulationDto simulation = (CreateSimulationDto) o;
        return simulation.getI() < simulation.getP();
    }
}
