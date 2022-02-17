package com.jinbkim.whoru.exception.customvalidator;

import com.jinbkim.whoru.contents.tests.domain.Tests;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class TestsValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Tests.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors,"nickname", "Notnull", "nickname이 없습니다");
        ValidationUtils.rejectIfEmpty(errors,"questionIds", "Notnull", "questionIds가 없습니다");
    }
}
