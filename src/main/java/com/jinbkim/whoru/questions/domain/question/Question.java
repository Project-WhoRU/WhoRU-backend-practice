package com.jinbkim.whoru.questions.domain.question;

import java.util.List;
import java.util.Map;
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
    private Examples examples;
    private String answer;

    @Builder
    public Question(QuestionType type, String question, Examples examples, String answer) {
        this.type = type;
        this.question = question;
        this.examples = examples;
        this.answer = answer;
    }
}