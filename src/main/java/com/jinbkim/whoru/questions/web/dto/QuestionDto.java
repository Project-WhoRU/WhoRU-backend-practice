package com.jinbkim.whoru.questions.web.dto;

import com.jinbkim.whoru.questions.domain.question.QuestionType;
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
    @NotNull
    private Object examples;
    @NotNull
    private Object answer;
}
