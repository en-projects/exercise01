package com.exercises.exercise01.util;

import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public final class EmailUtil {

    private static final String EMAIL_ADDRESS_REGEXP =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public static boolean isValidEmail(String emailAddress) {
        if (StringUtils.isEmpty(emailAddress)) {
            return false;
        }

        Pattern pattern = Pattern.compile(EMAIL_ADDRESS_REGEXP, Pattern.CASE_INSENSITIVE);
        return pattern.matcher(emailAddress).matches();
    }

    public EmailUtil() {
    }
}
