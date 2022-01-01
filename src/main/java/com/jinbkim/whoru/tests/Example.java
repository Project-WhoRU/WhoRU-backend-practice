package com.jinbkim.whoru.tests;

import java.util.List;

public class Example {
    private String question;
    private List<String> examples;

    public Example(String question, List<String> examples) {
        this.question = question;
        this.examples = examples;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getExamples() {
        return examples;
    }
}