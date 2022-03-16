package com.jinbkim.whoru.contents.questionlist.service;

import com.jinbkim.whoru.contents.questionlist.repository.QuestionListRepository;
import com.jinbkim.whoru.contents.questions.domain.question.Question;
import com.jinbkim.whoru.contents.questions.repository.QuestionRepository;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.questionlist.domain.QuestionList;
import com.jinbkim.whoru.contents.questionlist.web.dto.FindTestPageResponseDto;
import com.jinbkim.whoru.contents.users.domain.UsersImplement;
import com.jinbkim.whoru.contents.users.repository.UserRepository;
import com.jinbkim.whoru.exception.customexceptions.TestDoesntExistException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class QuestionListService {
    private final QuestionListRepository questionListRepository;
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
//        QuestionList tests = QuestionList.builder()
//                .nickname(testAddRequestDto.getNickname())
//                .questionIds(questionIds)
//                .build();
//        ExceptionThrow.exceptionThrow(tests, "tests");
//        questionListRepository.save(tests);
//
//        // testId 반환
//        return new TestAddResponseDto(tests.getId());
//    }

//    public List<QuestionDto> findQuestionList(String testId) {
//        QuestionList questionList = questionListRepository.findById(testId).orElseThrow(TestDoesntExistException::new);
//
//        // questionId로 questions 조회
//        return questionList.getQuestionIds().stream()
//            .map(questionRepository::findById)
//            .map(Optional::get)
//            .map(this::createQuestionDto)
//            .collect(Collectors.toList());
//    }

    private QuestionDto createQuestionDto(Question question) {
        return QuestionDto.builder()
            .type(question.getType())
            .question(question.getQuestion())
            .examples(question.getExamples())
            .answer(question.getAnswer())
            .build();
    }

//    public QuestionList findTest(String nickname) {
//        UsersImplement users = userRepository.findByNickname(nickname);
//        if (users == null)
//            return null;
//        QuestionList questionList = questionListRepository.findById(users.getTestId()).orElseGet(null);
//        return questionList;
//    }

//    public FindTestPageResponseDto findTestPage(String testId, String page) {
////        TestFindResponseDto testFindResponseDto = findTest(testId);
////        QuestionList tests = findTest(testId);
//
//        QuestionDto question = findQuestionList(testId).get(Integer.parseInt(page)-1);
//        Boolean isLastPage = Boolean.FALSE;
//
//        // 테스트의 질문 페이지가 마지막 이면
//        if (Integer.parseInt(page) == findQuestionList(testId).size())
//            isLastPage = Boolean.TRUE;
//
//        return new FindTestPageResponseDto(question, isLastPage);
//    }

//    public String findTestIdByNickname(String nickname) {
//        QuestionList tests = questionListRepository.findByNickname(nickname);
//        if (tests == null)
//            return null;
//        return tests.getId();
//    }

//    public TestGradeResponseDto gradeTest(TestGradeRequestDto testGradeRequestDto) {
//        // testId로 tests 조회
//        QuestionList tests = questionListRepository.findById(testGradeRequestDto.getTestId()).orElseThrow(TestDoesntExistException::new);
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
//        QuestionList tests = questionListRepository.findById(testId).orElseThrow(TestDoesntExistException::new);
//
//        // test의 모든 question 제거
//        List<String> questionIds = tests.getQuestionIds();
//        for (String questionId : questionIds)
//            questionRepository.delete(questionRepository.findById(questionId).get());
//
//        // tests 제거
//        questionListRepository.delete(tests);
//    }

//    public String setNickname(TestSetNicknameRequestDto testSetNicknameRequestDto) {
        // 테스트에 일단 아이디만 넣기
//        QuestionList test = QuestionList.builder()
//            .nickname(testSetNicknameRequestDto.getNickname())
//            .build();
//        questionListRepository.save(test);

        // 테스트 아이디 반환
//        return test.getId();

//        return null;
//    }

    public QuestionList createQuestionList() {
        QuestionList questionList = new QuestionList();
        questionListRepository.save(questionList);
        return questionList;
    }

//    public void addQuestionId(String testId, String questionId) {
//        // testId로 questionList 조회
//        QuestionList questionList = questionListRepository.findById(testId).orElseThrow(TestDoesntExistException::new);
//
//        // question 추가
//        questionList.addQuestion(questionId);
//        questionListRepository.save(questionList);
//    }

    public void addQuestion(QuestionList questionList, Question question) {
        questionList.addQuestion(question);
        questionListRepository.save(questionList);
    }

//    public void completeTest(String testId) {
//        // testId로 questionList 조회
//        QuestionList questionList = questionListRepository.findById(testId).orElseThrow(TestDoesntExistException::new);
//
//        // complete 세팅
//        questionList.setComplete(true);
//        questionListRepository.save(questionList);
//    }

    public String validateNicknameDuplicate(String nickname) {
//        if (questionListRepository.existsByNickname(nickname))
//            return "duplicate";
        return null;
    }

    public void deleteQuestionList(UsersImplement users) {
        this.questionListRepository.delete(users.getQuestionList());
        users.setQuestionList(null);
        this.userRepository.save(users);
    }

    public void questionListComplete(QuestionList questionList) {
        questionList.setComplete(Boolean.TRUE);
        questionListRepository.save(questionList);
    }

}