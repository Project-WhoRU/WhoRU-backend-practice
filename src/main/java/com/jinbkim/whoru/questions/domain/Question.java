package com.jinbkim.whoru.questions.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.lang.Nullable;

@Getter
@Setter
@Builder
public class Question {
    @Id
    private String id;

    private QuestionType type;
    private String question;
    @Nullable
    private Object examples;
    @Nullable
    private Object answer;
}