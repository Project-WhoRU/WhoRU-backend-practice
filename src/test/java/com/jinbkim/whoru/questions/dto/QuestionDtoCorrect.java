package com.jinbkim.whoru.questions.dto;

import com.jinbkim.whoru.questions.domain.question.QuestionType;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import java.util.ArrayList;
import java.util.Arrays;

public class QuestionDtoCorrect extends QuestionDto {

    public QuestionDtoCorrect() {
        super(QuestionType.MULTIPLE_CHOICE, "나의 이름은?", new ArrayList<>(Arrays.asList("jinbkim", "yapark", "hjung")), 0);
    }
}