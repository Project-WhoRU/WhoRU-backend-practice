package com.jinbkim.whoru.questions.dto;

import com.jinbkim.whoru.questions.domain.question.Examples;
import com.jinbkim.whoru.questions.domain.question.QuestionType;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;

public class QuestionDtoNoAnswer extends QuestionDto {

    public QuestionDtoNoAnswer() {
        super(QuestionType.MULTIPLE_CHOICE, "나의 이름은?", new Examples("jinbkim", "yapark", "hjung"), null);
    }
}
