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
    private String nickname;
    private List<String> questionIds = new ArrayList<>();;
    private Boolean complete= false;

    @Builder
    Tests(String nickname) {
        this.nickname = nickname;
    }

    public void addQuestion(String questionId) {
        questionIds.add(questionId);
    }
}
