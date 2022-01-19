package com.jinbkim.whoru.tests.dto;

import com.jinbkim.whoru.questions.dto.QuestionDtoCorrect;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.tests.web.dto.TestAddRequestDto;
import java.util.ArrayList;
import java.util.Arrays;

public class TestAddRequestDtoNoNickname extends TestAddRequestDto {

    public TestAddRequestDtoNoNickname() {
        super(null, new ArrayList<QuestionDto>(Arrays.asList(new QuestionDtoCorrect(), new QuestionDtoCorrect(), new QuestionDtoCorrect())));
    }
}
