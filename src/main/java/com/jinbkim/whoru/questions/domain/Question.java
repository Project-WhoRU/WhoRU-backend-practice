package com.jinbkim.whoru.questions.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
public class Question {
    @Id
    private String id;

    private String question;
    private List<String> examples;
    private int answer;

    public Question(String question, List<String> examples, int answer) {
        this.question = question;
        this.examples = examples;
        this.answer = answer;
    }
}
