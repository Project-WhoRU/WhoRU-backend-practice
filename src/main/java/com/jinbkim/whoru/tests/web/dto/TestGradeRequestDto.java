package com.jinbkim.whoru.tests.web.dto;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class TestGradeRequestDto {
    @NotNull(message = "tedId가 없습니다")
    private String testId;

    @NotNull(message = "answerSubmit이 없습니다")
    private List<Object> answerSubmit;
}
