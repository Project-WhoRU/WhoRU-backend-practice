package com.jinbkim.whoru.contents.graderesult.service;

import com.jinbkim.whoru.exception.customexceptions.GradeResultDoesntExistException;
import com.jinbkim.whoru.exception.customexceptions.QuestionDoesntExistException;
import com.jinbkim.whoru.exception.customexceptions.TestDoesntExistException;
import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.graderesult.repository.GradeResultRepository;
import com.jinbkim.whoru.contents.questions.domain.question.Question;
import com.jinbkim.whoru.contents.questions.repository.QuestionRepository;
import com.jinbkim.whoru.contents.tests.domain.Tests;
import com.jinbkim.whoru.contents.tests.repository.TestRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GradeResultService {
    private final GradeResultRepository gradeResultRepository;
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    public String setTestId(String testId) {
        // 채점 결과에 일단 테스트 아이디만 넣기
        GradeResult gradeResult = GradeResult.builder()
            .testId(testId)
            .build();
        gradeResultRepository.save(gradeResult);

        // 채점결과 아이디 반환
        return gradeResult.getId();
    }

    public void setNickname(String gradeResultId, String nickname) {
//        System.out.println("grade Id : " + gradeResultId);
//        List<GradeResult> all = gradeResultRepository.findAll();
//        for (GradeResult gradeResult : all) {
//            System.out.println("id : " + gradeResult.getId());
//        }


        // gradeResultI로 gradeResult 조회
        GradeResult gradeResult = gradeResultRepository.findById(gradeResultId).orElseThrow(GradeResultDoesntExistException::new);

        // 채점 결과에 닉네임 추가
        gradeResult.setResponseNickname(nickname);
        gradeResultRepository.save(gradeResult);
    }

    public void addAnswerSubmit(String gradeResultId, String page, String answer) {
        // gradeResultI로 gradeResult 조회
        GradeResult gradeResult = gradeResultRepository.findById(gradeResultId).orElseThrow(GradeResultDoesntExistException::new);

        if (Integer.parseInt(page) == gradeResult.getAnswerSubmit().size()+1)  // 새로운 문제의 정답 제출을 추가적으로 해야하는 상황
            gradeResult.addAnswerSubmit(answer);
        else if (Integer.parseInt(page) <= gradeResult.getAnswerSubmit().size())  // 이전 문제의 정답 제출을 수정해야 하는 상황
            gradeResult.updateAnswerSubmit(page, answer);
        else
            System.out.println("error!");
        gradeResultRepository.save(gradeResult);
    }

    public GradeResult gradeTest(String gradeResultId, String requesterNickname) {
        // gradeResultI로 gradeResult 조회
        GradeResult gradeResult = gradeResultRepository.findById(gradeResultId).orElseThrow(GradeResultDoesntExistException::new);
        List<String> answerList = gradeResult.getAnswerSubmit();
        String testId = gradeResult.getTestId();

        // testId로 tests 조회
        Tests tests = testRepository.findById(testId).orElseThrow(TestDoesntExistException::new);
        List<String> questionIds = tests.getQuestionIds();

        // 채점결과 저장
        gradeResult.setQuestionsCount(questionIds.size());
        List<Boolean> result = new ArrayList<>();
        int answerCount = 0;
        for (int i=0; i<questionIds.size(); i++) {
            Question question = questionRepository.findById(questionIds.get(i)).orElseThrow(QuestionDoesntExistException::new);
            if (question.getAnswer().equals(answerList.get(i))) { // 정답
                result.add(Boolean.TRUE);  // 정답 표시
                answerCount++;  // 정답수 증가
            }
            else  // 오답
                result.add(Boolean.FALSE);
        }
        gradeResult.setAnswersCount(answerCount);
        gradeResult.setGradeResult(result);

        gradeResult.setRequestNickname(requesterNickname);
        gradeResultRepository.save(gradeResult);
        return gradeResult;
    }

    public GradeResult findGradeResult(String gradeResultId) {
        return gradeResultRepository.findById(gradeResultId).orElseThrow(GradeResultDoesntExistException::new);
    }

    public void deleteGradeResult(String nickname) {
        List<GradeResult> gradeResultList = this.gradeResultRepository.findByRequestNickname(nickname).orElseGet(()->null);
        gradeResultList.stream().forEach(this.gradeResultRepository::delete);
    }
}
