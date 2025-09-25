package com.example.authtemplate.util;

import java.util.regex.Pattern;

public class PasswordValidator {

    private static final String PASSWORD_PATTERN =
            "^(?=.{8,}$)(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&\\-_\\.])[^\\s]+$";

    private static final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    public static boolean isValid(String password, String usernameOrEmail) {
        if (password == null) return false;
        if (!pattern.matcher(password).matches()) return false;
        if (usernameOrEmail != null) {
            String lower = usernameOrEmail.toLowerCase();
            return !password.toLowerCase().contains(lower);
        }
        return true;
    }
}
