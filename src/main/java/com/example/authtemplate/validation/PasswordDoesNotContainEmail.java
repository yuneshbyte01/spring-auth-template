package com.example.authtemplate.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordDoesNotContainEmailValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordDoesNotContainEmail {
    String message() default "Password must not contain your email.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
