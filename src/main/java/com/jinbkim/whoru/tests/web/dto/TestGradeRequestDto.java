package com.jinbkim.whoru.tests.web.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class TestGradeRequestDto {
    private String testId;
    private List<Object> answerSubmit;
}
