package com.jinbkim.whoru.contents.tests.service;

import com.jinbkim.whoru.contents.questions.domain.question.Question;
import com.jinbkim.whoru.contents.questions.repository.QuestionRepository;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.tests.domain.Tests;
import com.jinbkim.whoru.contents.tests.repository.TestRepository;
import com.jinbkim.whoru.contents.tests.web.dto.FindTestPageResponseDto;
import com.jinbkim.whoru.contents.tests.web.dto.TestFindResponseDto;
import com.jinbkim.whoru.contents.tests.web.dto.TestGradeRequestDto;
import com.jinbkim.whoru.contents.tests.web.dto.TestGradeResponseDto;
import com.jinbkim.whoru.contents.tests.web.dto.TestSetNicknameRequestDto;
import com.jinbkim.whoru.exception.customexceptions.AnswerSizeIsWrongException;
import com.jinbkim.whoru.exception.customexceptions.TestDoesntExistException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TestService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

//    public TestAddResponseDto addTest(TestAddRequestDto testAddRequestDto){
//        // questId 구하기
//        List<String> questionIds = new ArrayList<>();
//        List<QuestionDto> questions = testAddRequestDto.getQuestions();
//        for(QuestionDto questionDto: questions)
//            questionIds.add(questionService.addQuestion(questionDto));
//
//        // db에 tests 저장
//        Tests tests = Tests.builder()
//                .nickname(testAddRequestDto.getNickname())
//                .questionIds(questionIds)
//                .build();
//        ExceptionThrow.exceptionThrow(tests, "tests");
//        testRepository.save(tests);
//
//        // testId 반환
//        return new TestAddResponseDto(tests.getId());
//    }

    public TestFindResponseDto findTest(String testId) {
        // testId로 테스트의 questionIds 조회
        Tests tests = testRepository.findById(testId).orElseThrow(TestDoesntExistException::new);

        // questionId로 questions 조회
        List<QuestionDto> questionDtoList = tests.getQuestionIds().stream()
            .map(questionRepository::findById)
            .map(Optional::get)
            .map(this::createQuestionDto)
            .collect(Collectors.toList());

        // 구한 questions를 반환
        return new TestFindResponseDto(tests.getNickname(), questionDtoList);
    }

    private QuestionDto createQuestionDto(Question question) {
        return QuestionDto.builder()
            .type(question.getType())
            .question(question.getQuestion())
            .examples(question.getExamples())
            .answer(question.getAnswer())
            .build();
    }

    public FindTestPageResponseDto findTestPage(String testId, String page) {
        TestFindResponseDto testFindResponseDto = findTest(testId);
        QuestionDto question = findTest(testId).getQuestions().get(Integer.parseInt(page)-1);
        Boolean isLastPage = Boolean.FALSE;

        // 테스트의 질문 페이지가 마지막 이면
        if (Integer.parseInt(page) == testFindResponseDto.getQuestions().size())
            isLastPage = Boolean.TRUE;

        return new FindTestPageResponseDto(question, isLastPage);
    }

    public TestGradeResponseDto gradeTest(TestGradeRequestDto testGradeRequestDto) {
        // testId로 tests 조회
        Tests tests = testRepository.findById(testGradeRequestDto.getTestId()).orElseThrow(TestDoesntExistException::new);

        // 정답 수 세기
        int answerCount = 0;
        List<String> answerSubmit = testGradeRequestDto.getAnswerSubmit();
        if (answerSubmit.size() != tests.getQuestionIds().size())
            throw new AnswerSizeIsWrongException();
        for(int i=0; i<answerSubmit.size(); i++) {
            Question question = questionRepository.findById(tests.getQuestionIds().get(i)).get();
            if (answerSubmit.get(i).equals(question.getAnswer()))  // 정답이면
                answerCount++;
        }

        // 문제의 수, 정답의 수 반환
        return TestGradeResponseDto.builder()
                .questionsCount(answerSubmit.size())
                .answersCount(answerCount)
                .build();
    }

    public void removeTest(String testId) throws TestDoesntExistException {
        // testId로 tests 조회
        Tests tests = testRepository.findById(testId).orElseThrow(TestDoesntExistException::new);

        // test의 모든 question 제거
        List<String> questionIds = tests.getQuestionIds();
        for (String questionId : questionIds)
            questionRepository.delete(questionRepository.findById(questionId).get());

        // tests 제거
        testRepository.delete(tests);
    }

    public String setNickname(TestSetNicknameRequestDto testSetNicknameRequestDto) {
        // 테스트에 일단 아이디만 넣기
        Tests test = Tests.builder()
            .nickname(testSetNicknameRequestDto.getNickname())
            .build();
        testRepository.save(test);

        // 테스트 아이디 반환
        return test.getId();
    }

    public void addQuestionId(String testId, String questionId) {
        // testId로 tests 조회
        Tests tests = testRepository.findById(testId).orElseThrow(TestDoesntExistException::new);

        // question 추가
        tests.addQuestion(questionId);
        testRepository.save(tests);
    }

    public void completeTest(String testId) {
        // testId로 tests 조회
        Tests tests = testRepository.findById(testId).orElseThrow(TestDoesntExistException::new);

        // complete 세팅
        tests.setComplete(true);
        testRepository.save(tests);
    }

    public String validateNicknameDuplicate(String nickname) {
        if (testRepository.existsByNickname(nickname))
            return "duplicate";
        return null;
    }
}