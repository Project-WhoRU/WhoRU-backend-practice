package com.jinbkim.whoru.tests.web.controller;

import com.jinbkim.whoru.exception.customexceptions.NotnullException;
import com.jinbkim.whoru.exception.customexceptions.TestDoesntExistException;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.tests.service.TestService;
import com.jinbkim.whoru.tests.web.dto.*;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/tests")
public class TestController {
    private final TestService testService;

    @PostMapping
    public TestAddResponseDto TestAdd(@Valid @RequestBody TestAddRequestDto testAddRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            throw new NotnullException(bindingResult);
        try {
            return testService.addTest(testAddRequestDto);
        }
        catch (NotnullException e) {
            throw new NotnullException(e.getErrors());
        }
    }


    public TestFindResponseDto Testfind(@RequestParam(value = "testId") String testId) {
        try {
            return testService.findTest(testId);
        }
        catch (TestDoesntExistException e) {
            throw new TestDoesntExistException();
        }
    }

    @PostMapping("/grade")
    public TestGradeResponseDto TestGrade(@Valid @RequestBody TestGradeRequestDto testGradeRequestDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new NotnullException(bindingResult);
        }
        try {
            return testService.gradeTest(testGradeRequestDto);
        }
        catch (Exception e) {
            throw e;
        }
    }

    @DeleteMapping
    public void TestRemove(@RequestParam("testId") String testId) {
        try {
            testService.removeTest(testId);
        }
        catch (TestDoesntExistException e) {
            throw new TestDoesntExistException();
        }
    }
}
