package com.jinbkim.whoru.contents.questions.web.dto;

import com.jinbkim.whoru.contents.questions.domain.question.Examples;
import com.jinbkim.whoru.contents.questions.domain.question.QuestionType;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionDto {
    @NotNull
    private QuestionType type;
    @NotNull
    private String question;
    private Examples examples;
    @NotNull
    private String answer;

    public QuestionDto() {
        Examples examples = new Examples();
    }
}
