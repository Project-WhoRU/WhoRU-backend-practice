package com.jinbkim.whoru.questions.dto;

import com.jinbkim.whoru.contents.questions.domain.question.QuestionType;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;

public class QuestionDtoMultipleChoiceNoExamples extends QuestionDto {

    public QuestionDtoMultipleChoiceNoExamples() {
        super(QuestionType.MULTIPLE_CHOICE, "나의 이름은?", null, "jinbkim");
    }
}
