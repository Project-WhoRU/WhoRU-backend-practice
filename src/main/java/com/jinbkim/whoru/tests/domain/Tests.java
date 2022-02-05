package com.jinbkim.whoru.tests.domain;

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
    private List<String> questionIds;

    @Builder
    Tests(String nickname) {
        this.nickname = nickname;
        questionIds = new ArrayList<>();
    }

    public void addQuestion(String questionId) {
        questionIds.add(questionId);
    }
}
