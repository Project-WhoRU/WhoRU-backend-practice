package com.jinbkim.whoru.questions.dto;

import com.jinbkim.whoru.questions.domain.question.Examples;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;

public class QuestionDtoNoType extends QuestionDto {

    public QuestionDtoNoType() {
        super(null, "나의 이름은?", new Examples("jinbkim", "yapark", "hjung"), "1");
    }
}
