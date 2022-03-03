package com.jinbkim.whoru.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Component
@RequiredArgsConstructor
public class ValidatorInit {

    private final UserValidator userValidator;
    private final QuestionValidator questionValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(userValidator);
        dataBinder.addValidators(questionValidator);
    }
}
