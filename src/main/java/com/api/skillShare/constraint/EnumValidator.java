package com.api.skillShare.constraint;

import com.api.skillShare.enums.Expertise;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {

    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        try {
            Enum.valueOf(value.getDeclaringClass(), value.name());
            return true;
        } catch (IllegalArgumentException e) {
            return false; // Invalid enum value
        }
    }
}
