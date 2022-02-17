package com.jinbkim.whoru.contents.tests.web.dto;

import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class TestFindResponseDto {
    private String nickname;
    private List<QuestionDto> questions;
}