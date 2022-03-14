package com.jinbkim.whoru.contents.questions.web.dto;

import com.jinbkim.whoru.contents.questions.domain.question.Examples;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionDto {
    @NotBlank
    private String type;
    @NotBlank
    private String question;
    private Examples examples;
    @NotBlank
    private String answer;

    public QuestionDto() {
        Examples examples = new Examples();
    }
}
