package com.jinbkim.whoru.contents.tests.domain;

import java.util.ArrayList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

@Getter
@Setter
public class Tests {
    @Id
    private String id;
    private List<String> questionIds;
    private Boolean complete;

    public Tests() {
        questionIds = new ArrayList<>();
        complete = new Boolean(false);
    }

    public void addQuestion(String questionId) {
        questionIds.add(questionId);
    }
}
