package com.jinbkim.whoru.tests.service;

import com.jinbkim.whoru.questions.domain.question.Question;
import com.jinbkim.whoru.questions.domain.question.QuestionType;
import com.jinbkim.whoru.questions.repository.QuestionRepository;
import com.jinbkim.whoru.questions.service.QuestionService;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.tests.domain.Test;
import com.jinbkim.whoru.tests.repository.TestRepository;
import com.jinbkim.whoru.tests.web.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class TestService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;

    public TestService(TestRepository testRepository, QuestionRepository questionRepository, QuestionService questionService) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    public TestAddResponseDto addTest(@RequestBody TestAddRequestDto testAddRequestDto){
        // questId 구하기
        List<String> questionIds = new ArrayList<>();
        List<QuestionDto> questions = testAddRequestDto.getQuestions();
        for(QuestionDto questionDto: questions)
            questionIds.add(questionService.addQuestion(questionDto));

        // db에 test 저장
        Test test = Test.builder()
                .nickname(testAddRequestDto.getNickname())
                .questionIds(questionIds)
                .build();
        testRepository.save(test);

        // testId 반환
        return new TestAddResponseDto(test.getId());
    }

    public TestFindResponseDto findTest(String testId) {
        // testId로 테스트의 questionIds 조회
        Test test = testRepository.findById(testId).get();
        List<String> questionIds = test.getQuestionIds();

        // questionId로 questions 조회
        List<QuestionDto> questions = new ArrayList<QuestionDto>();
        for(String questionId: questionIds) {
            Question question = questionRepository.findById(questionId).get();
            QuestionDto questionDto = QuestionDto.builder()
                    .type(question.getType())
                    .question(question.getQuestion())
                    .build();
            if (questionDto.getType() == QuestionType.MULTIPLE_CHOICE)
                questionDto.setExamples(question.getExamples());
            questions.add(questionDto);
        }

        // 구한 questions를 반환
        return new TestFindResponseDto(questions);
    }

    public TestGradeResponseDto gradeTest(@RequestBody TestGradeRequestDto testGradeRequestDto) {
        // testId로 test 조회
        Test test = testRepository.findById(testGradeRequestDto.getTestId()).get();

        // 정답 수 세기
        int answerCount = 0;
        List<Object> answerSubmit = testGradeRequestDto.getAnswerSubmit();
        for(int i=0; i<answerSubmit.size(); i++) {
            Question question = questionRepository.findById(test.getQuestionIds().get(i)).get();
            if (questionService.gradeQuestion(question.getType(), answerSubmit.get(i), question.getAnswer()))  // 정답이면
                answerCount++;
        }

        // 문제의 수, 정답의 수 반환
        return TestGradeResponseDto.builder()
                .questionsCount(answerSubmit.size())
                .answersCount(answerCount)
                .build();
    }

    public void removeTest(@RequestParam String testId) {
        // testId로 test 조회
        Test test = testRepository.findById(testId).get();

        // test의 모든 question 제거
        List<String> questionIds = test.getQuestionIds();
        for(int i=0; i<questionIds.size(); i++) {
            Question question = questionRepository.findById(questionIds.get(i)).get();
            questionRepository.delete(question);
        }

        // test 제거
        testRepository.delete(test);
    }
}
