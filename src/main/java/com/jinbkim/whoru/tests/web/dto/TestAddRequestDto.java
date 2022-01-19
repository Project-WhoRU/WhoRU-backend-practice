package com.jinbkim.whoru.tests.web.dto;


import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
public class TestAddRequestDto {
    private String nickname;
    private List<QuestionDto> questions;
}
