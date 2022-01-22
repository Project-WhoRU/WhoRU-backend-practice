package com.jinbkim.whoru.questions.domain.question;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Question {
    @Id
    private String id;
    private QuestionType type;
    private String question;
    private Object examples;
    private Object answer;

    @Builder
    public Question(QuestionType type, String question, Object examples, Object answer) {
        this.type = type;
        this.question = question;
        this.examples = examples;
        this.answer = answer;
    }
}