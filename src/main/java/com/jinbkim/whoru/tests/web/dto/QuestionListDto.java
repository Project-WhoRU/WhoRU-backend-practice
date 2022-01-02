package com.jinbkim.whoru.tests.web.dto;


import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import lombok.Getter;

import java.util.List;

@Getter
public class QuestionListDto {
    private String nickname;
    private List<QuestionDto> questionList;
}
