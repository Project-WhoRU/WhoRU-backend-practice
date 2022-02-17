package com.jinbkim.whoru.contents.tests.web.dto;

import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class FindTestPageResponseDto {
    private QuestionDto question;
    private Boolean isLastPage;
}
