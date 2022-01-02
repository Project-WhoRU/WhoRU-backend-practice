package com.jinbkim.whoru.questions.service;

import com.jinbkim.whoru.questions.domain.Question;
import com.jinbkim.whoru.questions.repository.QuestionRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionService {
    private final QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // 일단 무조건 성공
    public Map<String, Object> createQuestion(@RequestBody Map<String, Object> requestBody) {
        // db에 질문지 저장
        Question question = new Question((String) requestBody.get("question"), (List<String>) requestBody.get("examples"), (int) requestBody.get("answer"));
        questionRepository.save(question);

        // 성공시 question_id 반환
        Map<String, Object> response = new HashMap<String, Object>();
        response.put("question_id", question.getId());
        return response;
    }

    public Map<String, Object> updateQuestion(@RequestBody Map<String, Object> requestBody) {
        Optional<Question> question = questionRepository.findById((String)requestBody.get("question_id"));
        Map<String, Object> response = new HashMap<String, Object>();
        // 조회 하려는 question_id가 없으면
        if (!question.isPresent()) {
            response.put("success", false);
            return response;
        }

        // 질문지 정보 변경
        question.get().setQuestion((String) requestBody.get("question"));
        question.get().setExamples((List<String>) requestBody.get("examples"));
        question.get().setAnswer((int) requestBody.get("answer"));
        questionRepository.save(question.get());

        // 성공시 question_id 반환
        response.put("question_id", question.get().getId());
        return response;
    }

    public Map<String, Object> deleteQuestion(@RequestParam Map<String, Object> requestParam) {
        Optional<Question> question = questionRepository.findById((String)requestParam.get("question_id"));
        Map<String, Object> response = new HashMap<String, Object>();
        // 조회 하려는 question_id가 없으면
        if (!question.isPresent()) {
            response.put("success", false);
            return response;
        }

        questionRepository.delete(question.get());
        response.put("success", true);
        return response;
    }
}