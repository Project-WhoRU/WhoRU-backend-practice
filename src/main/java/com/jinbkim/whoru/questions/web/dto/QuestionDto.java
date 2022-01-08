package com.jinbkim.whoru.questions.web.dto;

import com.jinbkim.whoru.questions.domain.question.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionDto {
    private QuestionType type;
    private String question;
    @Nullable
    private Object examples;
    @Nullable
    private Object answer;
}
