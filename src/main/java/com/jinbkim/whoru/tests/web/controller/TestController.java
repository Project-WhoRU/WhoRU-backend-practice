package com.jinbkim.whoru.tests.web.controller;

import com.jinbkim.whoru.questions.service.QuestionService;
import com.jinbkim.whoru.tests.service.TestService;
import com.jinbkim.whoru.tests.web.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/tests")
public class TestController {
    private final QuestionService questionService;
    private final TestService testService;

    public TestController(QuestionService questionService, TestService testService) {
        this.questionService = questionService;
        this.testService = testService;
    }

    @PostMapping()
    public TestAddResponseDto TestAdd(@RequestBody TestAddRequestDto testAddRequestDto) {
        return testService.addTest(testAddRequestDto);
    }

    @GetMapping()
    public TestFindResponseDto Testfind(@RequestParam("testId") String testId) {
        return testService.findTest(testId);
    }

    @PostMapping("/grade")
    public TestGradeResponseDto TestGrade(@RequestBody TestGradeRequestDto testGradeRequestDto) {
        return testService.gradeTest(testGradeRequestDto);
    }

    @DeleteMapping
    public void TestRemove(@RequestParam("testId") String testId) {
        testService.removeTest(testId);
    }
}
