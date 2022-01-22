package com.jinbkim.whoru.exception.utils;

import com.jinbkim.whoru.exception.customexceptions.NotnullException;
import com.jinbkim.whoru.exception.customvalidator.QuestionValidator;
import com.jinbkim.whoru.exception.customvalidator.TestsValidator;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ExceptionThrow {
    public static void exceptionThrow(Object target, String targetName) {
        Errors errors = new BeanPropertyBindingResult(target, targetName);
        Validator validator = getValidator(targetName);
        validator.validate(target, errors);
        if (errors.hasErrors())
            throw new NotnullException(errors);
    }

    private static Validator getValidator(String targetName) {
        switch (targetName) {
            case "question":
                return new QuestionValidator();
            case "tests":
                return new TestsValidator();
            default:
                return null;
        }
    }
}
