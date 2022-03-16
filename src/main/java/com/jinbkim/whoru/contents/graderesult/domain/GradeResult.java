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
//    private String testId;
//    private String requestNickname;
//    private String responseNickname;
    private List<String> answerSubmit;
    private List<Boolean> gradeResult;
    private int questionsCount;
    private int answersCount;

    @Builder
    public GradeResult() {
//        this.testId = testId;
//        this.responseNickname = responseNickname;
        answerSubmit = new ArrayList<>();
        gradeResult = new ArrayList<>();
        answersCount = 0;
    }

    public void addAnswerSubmit(String answer) {
        answerSubmit.add(answer);
    }

    public void updateAnswerSubmit(String page, String answer) {
        answerSubmit.set(Integer.parseInt(page)-1, answer);
    }


}
