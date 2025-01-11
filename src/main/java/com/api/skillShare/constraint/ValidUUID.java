package com.api.skillShare.constraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import jakarta.validation.constraints.NotBlank;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {UUIDValidator.class})
@Target({ ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@NotBlank
public @interface ValidUUID {
    String EXAMPLE = "ddeb7f77-0d3f-4e57-8760-bdbd616a3c9f";
    String message() default "Invalid id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
