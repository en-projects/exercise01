package com.exercises.exercise01.util;

import com.google.common.base.Joiner;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.passay.*;

import java.util.*;
import java.util.stream.Collectors;

public final class PasswordUtil {

    private static final Logger log = LogManager.getLogger(PasswordUtil.class);

    private static final PasswordValidator PASSWORD_VALIDATOR;

    static {

        //Rule 1: Password length should be at least 8 characters
        LengthRule lengthRule = new LengthRule();
        lengthRule.setMinimumLength(8);

        CharacterCharacteristicsRule charRule = new CharacterCharacteristicsRule();

        //Rule 2.a: One Upper-case character
        charRule.getRules().add(new CharacterRule(EnglishCharacterData.UpperCase, 1){

        });
        //Rule 2.b: One Lower-case character
        charRule.getRules().add(new CharacterRule(EnglishCharacterData.LowerCase, 1));
        //Rule 2.c: One digit
        charRule.getRules().add(new CharacterRule(EnglishCharacterData.Digit, 1));
        //Rule 2.d: One special character
        charRule.getRules().add(new CharacterRule(EnglishCharacterData.Special, 1));

        // group all rules together in a List
        List<Rule> ruleList = new ArrayList<>();
        ruleList.add(lengthRule);
        ruleList.add(charRule);

        PASSWORD_VALIDATOR = new PasswordValidator(ruleList);
    }


    public static boolean isPasswordStrongEnough(String password) {
        password = StringUtils.defaultString(password);
        PasswordData passwordData = new PasswordData(password);

        RuleResult result = PASSWORD_VALIDATOR.validate(passwordData);

        if (!result.isValid()) {
            log.debug("Invalid password (weak): " + Joiner.on(',').join(weakPasswordMessages(password)));
        }
        return result.isValid();
    }

    public static List<String> weakPasswordMessages(String password) {
        try {
            password = StringUtils.defaultString(password);
            PasswordData passwordData = new PasswordData(password);
            RuleResult result = PASSWORD_VALIDATOR.validate(passwordData);
            List<String> messages = PASSWORD_VALIDATOR.getMessages(result);
            return messages
                    .stream()
                    .filter(StringUtils::isNotEmpty)
                    .distinct()
                    .collect(Collectors.toList());
        } catch (Exception ignored) {
            log.error("Failed building messages of week password - ERROR IGNORED. error: " + ignored.getMessage());
            return Collections.emptyList();
        }
    }

    public PasswordUtil() {
    }
}
