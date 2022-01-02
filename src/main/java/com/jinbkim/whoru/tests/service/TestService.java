package com.jinbkim.whoru.tests.service;

import com.jinbkim.whoru.questions.domain.Question;
import com.jinbkim.whoru.questions.repository.QuestionRepository;
import com.jinbkim.whoru.tests.domain.Test;
import com.jinbkim.whoru.tests.repository.TestRepository;
import com.jinbkim.whoru.tests.web.dto.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Service
public class TestService {
    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;

    public TestService(TestRepository testRepository, QuestionRepository questionRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
    }

    // 일단 무조건 성공
    public Map<String, Object> createTest(@RequestBody Map<String, Object> requestBody){
        // db에 질문지 테스트 저장
        Test test = new Test((String)requestBody.get("nickname") ,(List<String>) requestBody.get("questions"));
        testRepository.save(test);

        Map<String, Object> isSuccess = new HashMap<String, Object>();
        isSuccess.put("success", true);
        return isSuccess;
    }

    public Map<String, Object> readTest(@RequestParam Map<String, Object> requestParam) {
        Optional<Test> test = testRepository.findById((String)requestParam.get("nickname"));
        Map<String, Object> response = new HashMap<String, Object>();
        // 해당 닉네임의 테스트가 없으면
        if (!test.isPresent()) {
            response.put("success", false);
            return response;
        }

        // question_id로 test 조회
        List<String> question_id = test.get().getQuestions();
        List<Example> examples = new ArrayList<Example>();
        for(int i=0 ; i<question_id.size(); i++) {
            Optional<Question> question = questionRepository.findById(question_id.get(i));
            if (!question.isPresent())  // 조회하려는 question_id가 없으면
                continue;
            Example example = new Example(question.get().getQuestion(), question.get().getExamples());
            examples.add(example);
        }
        response.put("questions", examples);
        return response;
    }

    public Map<String, Object> readAnswer(@RequestBody Map<String, Object> requestBody) {
        Optional<Test> test = testRepository.findById((String)requestBody.get("nickname"));
        Map<String, Object> response = new HashMap<String, Object>();
        List<Integer> exampleSelect = (List<Integer>)requestBody.get("exampleSelect");
        // 해당 닉네임의 테스트가 없으면
        // 정답을 비교하려는 문제수가 다르면
        if (!test.isPresent() || exampleSelect.size() != test.get().getQuestions().size()) {
            response.put("success", false);
            return response;
        }

        // 정답의 수 세기
        int answerCount = 0;
        for(int i=0; i<exampleSelect.size(); i++) {
            String question_id = test.get().getQuestions().get(i);
            Optional<Question> question = questionRepository.findById(question_id);
            if (!question.isPresent())  // 조회하려는 question_id가 없으면
                continue;
            if (question.get().getAnswer() == exampleSelect.get(i))  // 정답이면
                answerCount++;
        }
        response.put("questions", exampleSelect.size());
        response.put("answers", answerCount);
        return response;
    }

    public Map<String, Object> deleteTest(@RequestParam Map<String, Object> requestParam) {
        Optional<Test> test = testRepository.findById((String)requestParam.get("nickname"));
        Map<String, Object> response = new HashMap<String, Object>();
        // 조회 하려는 nickname이 없으면
        if (!test.isPresent()) {
            response.put("success", false);
            return response;
        }

        testRepository.delete(test.get());
        response.put("success", true);
        return response;
    }
}
