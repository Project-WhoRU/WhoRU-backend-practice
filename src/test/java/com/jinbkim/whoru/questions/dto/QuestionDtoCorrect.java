package com.jinbkim.whoru.questions.dto;

import com.jinbkim.whoru.questions.domain.question.Examples;
import com.jinbkim.whoru.questions.domain.question.QuestionType;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;

public class QuestionDtoCorrect extends QuestionDto {

    public QuestionDtoCorrect() {
        super(QuestionType.MULTIPLE_CHOICE, "나의 이름은?", new Examples("jinbkim", "yapark", "hjung"), "1");
    }
}
