package com.jinbkim.whoru.questions.domain.question;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Question {
    @Id
    private String id;
    @NotNull
    private QuestionType type;
    @NotBlank
    private String question;
    private Object examples;
    @NotNull
    private Object answer;
    @Builder
    public Question(QuestionType type, String question, Object examples, Object answer) {
        this.type = type;
        this.question = question;
        this.examples = examples;
        this.answer = answer;

        if (type == QuestionType.MULTIPLE_CHOICE && examples == null)
            throw new NullPointerException();
    }
}