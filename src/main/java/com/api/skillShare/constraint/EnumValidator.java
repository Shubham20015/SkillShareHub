package com.api.skillShare.constraint;

import com.api.skillShare.enums.Expertise;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, String> {

    @Override
    public void initialize(ValidEnum constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            Expertise expertise = Expertise.valueOf(value); // Try to parse the string to an enum
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
