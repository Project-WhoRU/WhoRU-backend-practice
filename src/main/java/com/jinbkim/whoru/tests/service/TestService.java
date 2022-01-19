package com.jinbkim.whoru.tests.service;

import com.jinbkim.whoru.questions.domain.question.Question;
import com.jinbkim.whoru.questions.repository.QuestionRepository;
import com.jinbkim.whoru.questions.service.QuestionService;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.tests.domain.Tests;
import com.jinbkim.whoru.tests.repository.TestRepository;
import com.jinbkim.whoru.tests.web.dto.*;
import java.util.stream.Collectors;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TestService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final QuestionService questionService;
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public TestService(TestRepository testRepository, QuestionRepository questionRepository, QuestionService questionService) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.questionService = questionService;
    }

    public TestAddResponseDto addTest(TestAddRequestDto testAddRequestDto){
        // questId 구하기
        List<String> questionIds = new ArrayList<>();
        List<QuestionDto> questions = testAddRequestDto.getQuestions();
        for(QuestionDto questionDto: questions)
            questionIds.add(questionService.addQuestion(questionDto));

        // db에 tests 저장
        Tests tests = Tests.builder()
                .nickname(testAddRequestDto.getNickname())
                .questionIds(questionIds)
                .build();
        if (validator.validate(tests).size() > 0)
            throw new NullPointerException();
        testRepository.save(tests);

        // testId 반환
        return new TestAddResponseDto(tests.getId());
    }

    public TestFindResponseDto findTest(String testId) {
        // testId로 테스트의 questionIds 조회
        Optional<Tests> testsOptional = testRepository.findById(testId);
        if (!testsOptional.isPresent())
            throw new NullPointerException();
        Tests tests = testsOptional.get();

        // questionId로 questions 조회
        List<QuestionDto> questionDtoList = tests.getQuestionIds().stream()
            .map(questionRepository::findById)
            .map(Optional::get)
            .map(this::createQuestionDto)
            .collect(Collectors.toList());

        // 구한 questions를 반환
        return new TestFindResponseDto(questionDtoList);
    }

    private QuestionDto createQuestionDto(Question question) {
        return QuestionDto.builder()
            .type(question.getType())
            .question(question.getQuestion())
            .examples(question.getExamples())
            .answer(question.getAnswer())
            .build();
    }

    public TestGradeResponseDto gradeTest(TestGradeRequestDto testGradeRequestDto) {
        // testId로 tests 조회
        Optional<Tests> testsOptional = testRepository.findById(testGradeRequestDto.getTestId());
        if (!testsOptional.isPresent())
            throw new NullPointerException();
        Tests tests = testsOptional.get();

        // 정답 수 세기
        int answerCount = 0;
        List<Object> answerSubmit = testGradeRequestDto.getAnswerSubmit();
        if (answerSubmit.size() != tests.getQuestionIds().size())
            throw new ArrayIndexOutOfBoundsException();
        for(int i=0; i<answerSubmit.size(); i++) {
            Question question = questionRepository.findById(tests.getQuestionIds().get(i)).get();
            if (questionService.gradeQuestion(question.getType(), answerSubmit.get(i), question.getAnswer()))  // 정답이면
                answerCount++;
        }

        // 문제의 수, 정답의 수 반환
        return TestGradeResponseDto.builder()
                .questionsCount(answerSubmit.size())
                .answersCount(answerCount)
                .build();
    }

    public void removeTest(String testId) {
        // testId로 tests 조회
        Optional<Tests> testsOptional = testRepository.findById(testId);
        if (!testsOptional.isPresent())
            throw new NullPointerException();
        Tests tests = testsOptional.get();

        // test의 모든 question 제거
        List<String> questionIds = tests.getQuestionIds();
        for (String questionId : questionIds)
            questionRepository.delete(questionRepository.findById(questionId).get());

        // tests 제거
        testRepository.delete(tests);
    }
}