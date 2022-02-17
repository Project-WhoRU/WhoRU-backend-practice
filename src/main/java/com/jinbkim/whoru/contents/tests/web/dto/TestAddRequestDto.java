package com.jinbkim.whoru.contents.tests.web.dto;


import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TestAddRequestDto {
    @NotNull(message = "nickname이 없습니다")
    private String nickname;

    @NotNull(message = "questionIds가 없습니다")
    private List<QuestionDto> questions;
}
