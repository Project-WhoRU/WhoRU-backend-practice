package com.jinbkim.whoru.contents.users.domain;

import com.jinbkim.whoru.contents.questionlist.domain.QuestionList;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
public class Users {

    @Id
    protected String id;
    protected String nickname;
    protected String password;
    protected QuestionList questionList;

    public QuestionList completeCheckAndGetQuestionList() {
        if (questionList.isComplete() == false) {
            questionList = new QuestionList();
        }
        return questionList;
    }
}
