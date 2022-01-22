package com.jinbkim.whoru.exception.customvalidator;

import com.jinbkim.whoru.exception.error.ErrorCode;
import com.jinbkim.whoru.questions.domain.question.Question;
import com.jinbkim.whoru.questions.domain.question.QuestionType;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class QuestionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Question.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "type", "Notnull", "type이 없습니다");
        ValidationUtils.rejectIfEmpty(errors, "question", "Notnull", "question이 없습니다");
        ValidationUtils.rejectIfEmpty(errors, "answer", "Notnull", "answer이 없습니다");

        Question question = (Question)target;
        if (question.getType() == QuestionType.MULTIPLE_CHOICE)
            ValidationUtils.rejectIfEmpty(errors, "examples", "Notnull", "다지선다 문제에서 examples가 없습니다");
    }
}