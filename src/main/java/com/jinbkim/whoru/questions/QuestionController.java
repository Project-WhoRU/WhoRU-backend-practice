package com.jinbkim.whoru.questions;

import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/questions")
public class QuestionController {
    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public Map<String, Object> createQuestion(@RequestBody Map<String, Object> requestBody) {
        return questionService.createQuestion(requestBody);
    }

    @PostMapping("/update")
    public Map<String, Object> updateQuestion(@RequestBody Map<String, Object> requestBody) {
        return questionService.updateQuestion(requestBody);
    }

    @DeleteMapping
    public Map<String, Object> deleteQuestion(@RequestParam Map<String, Object> requestParam) {
        return questionService.deleteQuestion(requestParam);
    }
}
