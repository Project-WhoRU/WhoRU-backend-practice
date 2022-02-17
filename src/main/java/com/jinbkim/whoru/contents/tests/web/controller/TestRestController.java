package com.jinbkim.whoru.contents.tests.web.controller;

import com.jinbkim.whoru.contents.tests.web.dto.TestFindResponseDto;
import com.jinbkim.whoru.contents.tests.web.dto.TestGradeRequestDto;
import com.jinbkim.whoru.contents.tests.web.dto.TestGradeResponseDto;
import com.jinbkim.whoru.exception.customexceptions.NotnullException;
import com.jinbkim.whoru.exception.customexceptions.TestDoesntExistException;
import com.jinbkim.whoru.contents.tests.service.TestService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/tests")
public class TestRestController {
    private final TestService testService;

//    @PostMapping
//    public TestAddResponseDto TestAdd(@Valid @RequestBody TestAddRequestDto testAddRequestDto, BindingResult bindingResult) {
//        if (bindingResult.hasErrors())
//            throw new NotnullException(bindingResult);
//        try {
//            return testService.addTest(testAddRequestDto);
//        }
//        catch (NotnullException e) {
//            throw new NotnullException(e.getErrors());
//        }
//    }


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
