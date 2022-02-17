package com.jinbkim.whoru.contents.questions.service;

import com.jinbkim.whoru.contents.questions.domain.question.Question;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.exception.utils.ExceptionThrow;
import com.jinbkim.whoru.contents.questions.repository.QuestionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

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