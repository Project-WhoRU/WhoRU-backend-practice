package com.jinbkim.whoru.contents.tests.service;

import com.jinbkim.whoru.contents.questions.domain.question.Question;
import com.jinbkim.whoru.contents.questions.repository.QuestionRepository;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.tests.domain.Tests;
import com.jinbkim.whoru.contents.tests.repository.TestRepository;
import com.jinbkim.whoru.contents.tests.web.dto.FindTestPageResponseDto;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.repository.UserRepository;
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
    private final UserRepository userRepository;

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

    public List<QuestionDto> findQuestionList(String testId) {
        Tests tests = testRepository.findById(testId).orElseThrow(TestDoesntExistException::new);

        // questionId로 questions 조회
        return tests.getQuestionIds().stream()
            .map(questionRepository::findById)
            .map(Optional::get)
            .map(this::createQuestionDto)
            .collect(Collectors.toList());
    }

    private QuestionDto createQuestionDto(Question question) {
        return QuestionDto.builder()
            .type(question.getType())
            .question(question.getQuestion())
            .examples(question.getExamples())
            .answer(question.getAnswer())
            .build();
    }

    public Tests findTest(String nickname) {
        Users users = userRepository.findByNickname(nickname);
        if (users == null)
            return null;
        Tests tests = testRepository.findById(users.getTestId()).orElseGet(null);
        return tests;
    }

    public FindTestPageResponseDto findTestPage(String testId, String page) {
//        TestFindResponseDto testFindResponseDto = findTest(testId);
//        Tests tests = findTest(testId);

        QuestionDto question = findQuestionList(testId).get(Integer.parseInt(page)-1);
        Boolean isLastPage = Boolean.FALSE;

        // 테스트의 질문 페이지가 마지막 이면
        if (Integer.parseInt(page) == findQuestionList(testId).size())
            isLastPage = Boolean.TRUE;

        return new FindTestPageResponseDto(question, isLastPage);
    }

//    public String findTestIdByNickname(String nickname) {
//        Tests tests = testRepository.findByNickname(nickname);
//        if (tests == null)
//            return null;
//        return tests.getId();
//    }

//    public TestGradeResponseDto gradeTest(TestGradeRequestDto testGradeRequestDto) {
//        // testId로 tests 조회
//        Tests tests = testRepository.findById(testGradeRequestDto.getTestId()).orElseThrow(TestDoesntExistException::new);
//
//        // 정답 수 세기
//        int answerCount = 0;
//        List<String> answerSubmit = testGradeRequestDto.getAnswerSubmit();
//        if (answerSubmit.size() != tests.getQuestionIds().size())
//            throw new AnswerSizeIsWrongException();
//        for(int i=0; i<answerSubmit.size(); i++) {
//            Question question = questionRepository.findById(tests.getQuestionIds().get(i)).get();
//            if (answerSubmit.get(i).equals(question.getAnswer()))  // 정답이면
//                answerCount++;
//        }
//
//        // 문제의 수, 정답의 수 반환
//        return TestGradeResponseDto.builder()
//                .questionsCount(answerSubmit.size())
//                .answersCount(answerCount)
//                .build();
//    }

//    public void removeTest(String testId) throws TestDoesntExistException {
//        // testId로 tests 조회
//        Tests tests = testRepository.findById(testId).orElseThrow(TestDoesntExistException::new);
//
//        // test의 모든 question 제거
//        List<String> questionIds = tests.getQuestionIds();
//        for (String questionId : questionIds)
//            questionRepository.delete(questionRepository.findById(questionId).get());
//
//        // tests 제거
//        testRepository.delete(tests);
//    }

//    public String setNickname(TestSetNicknameRequestDto testSetNicknameRequestDto) {
        // 테스트에 일단 아이디만 넣기
//        Tests test = Tests.builder()
//            .nickname(testSetNicknameRequestDto.getNickname())
//            .build();
//        testRepository.save(test);

        // 테스트 아이디 반환
//        return test.getId();

//        return null;
//    }

    public Tests addTest() {
        Tests tests = new Tests();
        testRepository.save(tests);
        return tests;
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
//        if (testRepository.existsByNickname(nickname))
//            return "duplicate";
        return null;
    }
}