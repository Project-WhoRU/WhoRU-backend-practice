package com.jinbkim.whoru.questions.dto;

import com.jinbkim.whoru.questions.domain.question.Examples;
import com.jinbkim.whoru.questions.domain.question.QuestionType;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;

public class QuestionDtoNoQuestion extends QuestionDto {

    public QuestionDtoNoQuestion() {
        super(QuestionType.MULTIPLE_CHOICE, null, new Examples("jinbkim", "yapark", "hjung"), "1");
    }
}
