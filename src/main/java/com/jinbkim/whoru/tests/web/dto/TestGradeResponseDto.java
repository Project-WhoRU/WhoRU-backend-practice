package com.jinbkim.whoru.tests.web.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TestGradeResponseDto {
    private int questionsCount;
    private int answersCount;
}
