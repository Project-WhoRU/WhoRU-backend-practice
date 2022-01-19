package com.jinbkim.whoru.questions.dto;

import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import java.util.ArrayList;
import java.util.Arrays;

public class QuestionDtoNoType extends QuestionDto {

    public QuestionDtoNoType() {
        super(null, "나의 이름은?", new ArrayList<>(Arrays.asList("jinbkim", "yapark", "hjung")), 0);
    }
}
