package com.jinbkim.whoru.validator;

import com.jinbkim.whoru.contents.questionlist.domain.Examples;
import com.jinbkim.whoru.contents.questionlist.web.dto.QuestionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

// 적절한 질문이 입력으로 들어왔는지 검증
@Component
@RequiredArgsConstructor
public class QuestionValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return QuestionDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        QuestionDto question = (QuestionDto) target;
        Examples examples = question.getExamples();

        if (examples == null) {
            return;
        }
        if (examples.getEx1().isEmpty()) {
            errors.rejectValue("examples.ex1", "notblank", null);
        }
        if (examples.getEx2().isEmpty()) {
            errors.rejectValue("examples.ex2", "notblank", null);
        }
        if (examples.getEx3().isEmpty()) {
            errors.rejectValue("examples.ex3", "notblank", null);
        }
    }
}
