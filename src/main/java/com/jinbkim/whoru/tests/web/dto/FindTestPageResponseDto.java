package com.jinbkim.whoru.tests.web.dto;

import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindTestPageResponseDto {
    private QuestionDto question;
    private Boolean isLastPage;
}
