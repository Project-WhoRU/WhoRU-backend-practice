package com.jinbkim.whoru.contents.questionlist.domain;

import com.jinbkim.whoru.contents.questions.domain.question.Question;
import java.util.ArrayList;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class QuestionList {
    @Id
    private String id;
//    private List<String> questionIds;
    private List<Question> questions;
    private boolean complete;


    public QuestionList() {
//        questionIds = new ArrayList<>();
        questions = new ArrayList<>();
        complete = false;
    }

    public void addQuestion(Question question) {
        questions.add(question);
    }

}
