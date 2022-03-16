package com.jinbkim.whoru.contents.questionlist.web.dto;

import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class TestFindResponseDto {
    private String nickname;
    private List<QuestionDto> questions;
}
