package com.jinbkim.whoru.contents.questionlist.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class QuestionList {

    @Id
    private String id;
    private List<Question> questions;
    private boolean complete;


    // constructor
    public QuestionList() {
        questions = new ArrayList<>();
        complete = false;
    }


    // etc
    public void addQuestion(Question question) {
        questions.add(question);
    }
}
