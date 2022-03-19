package com.jinbkim.whoru.questions;

import com.jinbkim.whoru.contents.questionlist.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class QuestionServiceTests {

    @Autowired
    QuestionRepository questionRepository;
    @Autowired

    @Test
    @DisplayName("addQuestion : 제대로된 질문 생성시")
    public void addQuestionCorrect() {
//        // given
//        QuestionDto questionDto = new QuestionDtoCorrect();
//
//        // when
//        String questionDtoId = questionService.addQuestion(questionDto);
//
//        // then
//        Question question = questionRepository.findById(questionDtoId).get();
//        Assertions.assertEquals(questionDto.getType(), question.getType());
//        Assertions.assertEquals(questionDto.getQuestion(), question.getQuestion());
//        Assertions.assertEquals(questionDto.getExamples().getEx1(), question.getExamples().getEx1());
//        Assertions.assertEquals(questionDto.getExamples().getEx2(), question.getExamples().getEx2());
//        Assertions.assertEquals(questionDto.getExamples().getEx3(), question.getExamples().getEx3());
//        Assertions.assertEquals(questionDto.getAnswer(), question.getAnswer());
    }

//    @Test
//    @DisplayName("addQuestion : 질문을 생성할때, null 값이 들어오는 경우 예외 발생")
//    public void addQuestionNullValue() {
//        // given
//        // null값이 들어간 질문지 3개 생성
//        QuestionDto questionDtoNoAnswer = new QuestionDtoNoAnswer();
//        QuestionDto questionDtoNoQuestion = new QuestionDtoNoQuestion();
//        QuestionDto questionDtoNoType = new QuestionDtoNoType();
//
//        // when
//        // then
//        Assertions.assertThrows(Exception.class, () -> {
//            questionService.addQuestion(questionDtoNoAnswer);
//        });
//        Assertions.assertThrows(Exception.class, () -> {
//            questionService.addQuestion(questionDtoNoQuestion);
//        });
//        Assertions.assertThrows(Exception.class, () -> {
//            questionService.addQuestion(questionDtoNoType);
//        });
//    }

//    @Test
//    @DisplayName("addQuestion : 다지선다 질문을 생성할때 예시가 없을 경우 예외 발생")
//    public void addQuestionMultipleChoiceNoExample() {
//        // given
//        // 예시가 없는 다지선다 질문지 생성
//        QuestionDto questionDto = new QuestionDtoMultipleChoiceNoExamples();
//
//        // when
//        // then
//        Assertions.assertThrows(Exception.class, () -> {
//            questionService.addQuestion(questionDto);
//        });
//    }
}
