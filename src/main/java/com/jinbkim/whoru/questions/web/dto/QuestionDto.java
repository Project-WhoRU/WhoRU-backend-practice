package com.jinbkim.whoru.questions.web.dto;

import com.jinbkim.whoru.questions.domain.question.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class QuestionDto {
    private QuestionType type;
    private String question;
    private Object examples;
    private Object answer;
}
