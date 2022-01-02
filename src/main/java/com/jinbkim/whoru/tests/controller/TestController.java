package com.jinbkim.whoru.tests.controller;

import com.jinbkim.whoru.questions.service.QuestionService;
import com.jinbkim.whoru.tests.service.TestService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/tests")
public class TestController {
    public TestController(QuestionService questionService, TestService testService) {
        this.questionService = questionService;
        this.testService = testService;
    }

    private final QuestionService questionService;
    private final TestService testService;

    @PostMapping()
    public Map<String, Object> createTest(@RequestBody Map<String, Object> requestBody) {
        return testService.createTest(requestBody);
    }

    @GetMapping()
    public Map<String, Object> readTest(@RequestParam Map<String, Object> requestParam) {
        return testService.readTest(requestParam);
    }

    @PostMapping("/answer")
    public Map<String, Object> readAnswer(@RequestBody Map<String, Object> requestBody) {
        return testService.readAnswer(requestBody);
    }

    @DeleteMapping
    public Map<String, Object> deleteTest(@RequestParam Map<String, Object> requestParam) {
        return testService.deleteTest(requestParam);
    }
}
