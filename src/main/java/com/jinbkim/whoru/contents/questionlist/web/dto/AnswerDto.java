package com.jinbkim.whoru.contents.questionlist.web.dto;

import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AnswerDto {
    @NotBlank
    private String answer;
}
