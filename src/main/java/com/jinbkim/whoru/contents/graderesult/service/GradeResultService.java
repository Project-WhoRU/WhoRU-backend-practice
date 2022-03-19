package com.jinbkim.whoru.contents.graderesult.service;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.graderesult.repository.GradeResultRepository;
import com.jinbkim.whoru.contents.questionlist.domain.Question;
import com.jinbkim.whoru.contents.questionlist.domain.QuestionList;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.utils.web.dto.Views;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class GradeResultService {

    private final GradeResultRepository gradeResultRepository;

    // create
    public GradeResult createGradeResult() {
        GradeResult gradeResult = GradeResult.builder().build();
        gradeResultRepository.save(gradeResult);
        return gradeResult;
    }


    // update
    public void setUser(GradeResult gradeResult, Users user) {
        gradeResult.setUsers(user);
        gradeResultRepository.save(gradeResult);
    }

    public void setNickname(GradeResult gradeResult, String nickname) {
        gradeResult.setNickname(nickname);
        gradeResultRepository.save(gradeResult);
    }

    public GradeResult grade(GradeResult gradeResult) {
        List<String> answerList = gradeResult.getAnswerSubmit();
        Users users = gradeResult.getUsers();
        QuestionList questionList = users.getQuestionList();

        List<Boolean> result = new ArrayList<>();
        int answerCount = 0;
        for (Question question : questionList.getQuestions()) {
            String answer = answerList.get(questionList.getQuestions().indexOf(question));
            if (question.getAnswer().equals(answer)) {
                result.add(Boolean.TRUE);
                answerCount++;
            } else {
                result.add(Boolean.FALSE);
            }
        }

        gradeResult.setAnswersCount(answerCount);
        gradeResult.setGradeResult(result);
        gradeResult.setComplete(true);
        gradeResultRepository.save(gradeResult);
        return gradeResult;
    }

    public void answerSubmit(GradeResult gradeResult, Integer page, String answer) {
        if (page == gradeResult.getAnswerSubmit().size() + 1)  // 새로운 문제의 정답 제출을 추가적으로 해야하는 상황
        {
            gradeResult.addAnswerSubmit(answer);
        } else if (page <= gradeResult.getAnswerSubmit().size())  // 이전 문제의 정답 제출을 수정해야 하는 상황
        {
            gradeResult.updateAnswerSubmit(page, answer);
        } else {
            log.error(Views.UNEXPECTED_ERROR);
        }
        gradeResultRepository.save(gradeResult);
    }


    // delete
    public void deleteGradeResult(Users user) {
        List<GradeResult> gradeResultList = this.gradeResultRepository.findByUsers(user);
        gradeResultList.stream().forEach(this.gradeResultRepository::delete);
    }

    public void deleteUnCompleteGradeResult(Users users) {
        List<GradeResult> gradeResultList = gradeResultRepository.findByUsers(users);
        for (GradeResult gradeResult : gradeResultList) {
            if (gradeResult.isComplete() == false) {
                gradeResultRepository.delete(gradeResult);
            }
        }
    }
}
