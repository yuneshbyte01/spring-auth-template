package com.example.authtemplate.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import com.example.authtemplate.util.PasswordValidator;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        return PasswordValidator.isValid(password, null); // username/email check will be handled separately
    }
}
