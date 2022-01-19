package com.jinbkim.whoru.tests.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TestGradeRequestDto {
    private String testId;
    private List<Object> answerSubmit;
}
