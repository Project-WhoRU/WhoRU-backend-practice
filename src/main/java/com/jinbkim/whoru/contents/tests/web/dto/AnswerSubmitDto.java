package com.jinbkim.whoru.contents.tests.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerSubmitDto {
    @NotBlank
    private String answer;
}
