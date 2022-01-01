package com.jinbkim.whoru.tests;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "tests")
public class Test {
    @Id
    private String nickname;
    private List<String> questions;

    public Test(String nickname, List<String> questions) {
        this.nickname = nickname;
        this.questions = questions;
    }

    public List<String> getQuestions() {
        return questions;
    }
}
