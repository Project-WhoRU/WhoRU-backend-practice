package com.jinbkim.whoru.questions.web.dto;

import com.jinbkim.whoru.questions.domain.question.Examples;
import com.jinbkim.whoru.questions.domain.question.QuestionType;
import java.util.List;
import java.util.Map;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
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
}
