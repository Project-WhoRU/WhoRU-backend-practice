package com.jinbkim.whoru.contents.questionlist.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class Question {

    @Id
    private String id;
    private String type;
    private String question;
    private Examples examples;
    private String answer;


    // constructor
    @Builder
    public Question(String type, String question, Examples examples, String answer) {
        this.type = type;
        this.question = question;
        this.examples = examples;
        this.answer = answer;
    }
}