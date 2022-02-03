package com.jinbkim.whoru.questions.service;

import com.jinbkim.whoru.exception.utils.ExceptionThrow;
import com.jinbkim.whoru.questions.domain.question.Question;
import com.jinbkim.whoru.questions.repository.QuestionRepository;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;

import org.springframework.stereotype.Service;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public String addQuestion(QuestionDto QuestionDto) {
        // db에 질문지 저장
        Question question = Question.builder()
            .type(QuestionDto.getType())
            .question(QuestionDto.getQuestion())
            .examples(QuestionDto.getExamples())
            .answer(QuestionDto.getAnswer())
            .build();
        ExceptionThrow.exceptionThrow(question, "question");
        questionRepository.save(question);

        // questionId 반환
        return question.getId();
    }
}