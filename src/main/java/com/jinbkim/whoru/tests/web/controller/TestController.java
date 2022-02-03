package com.jinbkim.whoru.tests.web.controller;

import com.jinbkim.whoru.exception.customexceptions.NotnullException;
import com.jinbkim.whoru.exception.customexceptions.TestDoesntExistException;
import com.jinbkim.whoru.tests.service.TestService;
import com.jinbkim.whoru.tests.web.dto.*;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@Controller
@RequestMapping("/api/tests")
public class TestController {
    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    @PostMapping("/init")
    public String TestInit(@Valid TestInitRequestDto testInitRequestDto, BindingResult bindingResult, HttpSession httpSession) {
        log.info("init controller: " + testInitRequestDto.getNickname());
        if (bindingResult.hasErrors())
            throw new NotnullException(bindingResult);
        try {
            String testId = testService.initTest(testInitRequestDto);
            httpSession.setAttribute("testId", testId);
            return "redirect:/questiontypes";
        }
        catch (NotnullException e) {
            throw new NotnullException(e.getErrors());
        }
    }

    @PostMapping()
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

    @GetMapping()
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
