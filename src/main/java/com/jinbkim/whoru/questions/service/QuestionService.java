package com.jinbkim.whoru.questions.service;

import com.jinbkim.whoru.questions.domain.Question;
import com.jinbkim.whoru.questions.domain.QuestionType;
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
        questionRepository.save(question);

        // questionId 반환
        return question.getId();
    }

    private boolean gradeMultipleChoiceQuestion(int answerSubmit, int answer) {
        if (answerSubmit == answer)
            return true;
        return false;
    }

    private boolean gradeShortAnswerQuestion(String answerSubmit, String answer) {
        if (answerSubmit.equals(answer))
            return true;
        return false;
    }

    private boolean gradeOXQuestion(boolean answerSubmit, boolean answer) {
        if (answerSubmit == answer)
            return true;
        return false;
    }

    public boolean gradeQuestion(QuestionType type, Object answerSubmit, Object answer) {
        if (type == QuestionType.MULTIPLE_CHOICE && answerSubmit instanceof Integer && answer instanceof Integer)
            return gradeMultipleChoiceQuestion((int)answerSubmit, (int)answer);
        else if (type == QuestionType.SHORT_ANSWER && answerSubmit instanceof String && answer instanceof String)
            return gradeShortAnswerQuestion((String)answerSubmit, (String)answer);
        else if (type == QuestionType.OX && answerSubmit instanceof Boolean && answer instanceof Boolean)
            return gradeOXQuestion((boolean)answerSubmit, (boolean)answer);
        return false;
    }
}