package com.jinbkim.whoru.contents.graderesult.domain;

import com.jinbkim.whoru.contents.users.domain.Users;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
public class GradeResult {

    @Id
    private String id;
    private Users users;
    private String nickname;
    private List<String> answerSubmit;
    private List<Boolean> gradeResult;
    private int questionsCount;
    private int answersCount;
    private boolean complete;

    // constructor
    @Builder
    public GradeResult() {
        answerSubmit = new ArrayList<>();
        gradeResult = new ArrayList<>();
        answersCount = 0;
        questionsCount = 0;
        complete = false;
    }


    // setter
    public void setUsers(Users users) {
        this.users = users;
        if (users.getQuestionList() == null) {
            questionsCount = 0;
        } else {
            questionsCount = users.getQuestionList().getQuestions().size();
        }
    }


    // etc
    public void addAnswerSubmit(String answer) {
        answerSubmit.add(answer);
    }

    public void updateAnswerSubmit(Integer page, String answer) {
        answerSubmit.set(page - 1, answer);
    }
}
