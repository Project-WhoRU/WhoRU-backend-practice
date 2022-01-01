package com.jinbkim.whoru.questions;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection="questions")
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

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getExamples() {
        return examples;
    }

    public int getAnswer() {
        return answer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setExamples(List<String> examples) {
        this.examples = examples;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }
}
