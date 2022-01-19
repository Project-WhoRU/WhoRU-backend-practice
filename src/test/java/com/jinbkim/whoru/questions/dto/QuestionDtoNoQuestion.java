package com.jinbkim.whoru.questions.dto;

import com.jinbkim.whoru.questions.domain.question.QuestionType;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import java.util.ArrayList;
import java.util.Arrays;

public class QuestionDtoNoQuestion extends QuestionDto {

    public QuestionDtoNoQuestion() {
        super(QuestionType.MULTIPLE_CHOICE, null, new ArrayList<>(Arrays.asList("jinbkim", "yapark", "hjung")), 0);
    }
}
