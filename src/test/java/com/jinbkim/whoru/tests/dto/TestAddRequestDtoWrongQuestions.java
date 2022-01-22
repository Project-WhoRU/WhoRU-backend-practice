package com.jinbkim.whoru.tests.dto;

import com.jinbkim.whoru.questions.dto.QuestionDtoCorrect;
import com.jinbkim.whoru.questions.dto.QuestionDtoNoQuestion;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.tests.web.dto.TestAddRequestDto;
import java.util.ArrayList;
import java.util.Arrays;

public class TestAddRequestDtoWrongQuestions extends TestAddRequestDto {

    public TestAddRequestDtoWrongQuestions() {
        super("jinbkim", new ArrayList<QuestionDto>(Arrays.asList(new QuestionDtoCorrect(), new QuestionDtoCorrect(), new QuestionDtoNoQuestion())));
    }
}