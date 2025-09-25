package com.example.authtemplate.validation;

import com.example.authtemplate.dto.RegisterRequest;
import com.example.authtemplate.util.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordDoesNotContainEmailValidator implements ConstraintValidator<PasswordDoesNotContainEmail, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest request, ConstraintValidatorContext context) {
        if (request == null) return true;
        return PasswordValidator.isValid(request.getPassword(), request.getEmail());
    }
}
