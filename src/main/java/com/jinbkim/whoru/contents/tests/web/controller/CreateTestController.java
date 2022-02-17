package com.jinbkim.whoru.contents.tests.web.controller;

import com.jinbkim.whoru.contents.questions.service.QuestionService;
import com.jinbkim.whoru.contents.tests.web.dto.TestSetNicknameRequestDto;
import com.jinbkim.whoru.exception.customexceptions.NotnullException;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.tests.service.TestService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/create")
public class CreateTestController {
    private final TestService testService;
    private final QuestionService questionService;
    @Value("${domain-address}")
    private String domainAddress;

    @PostMapping("/nickname")
    public String requesterNicknameSet(@Valid TestSetNicknameRequestDto testSetNicknameRequestDto, BindingResult bindingResult, HttpSession httpSession, Model model) {
        if (bindingResult.hasErrors())
            throw new NotnullException(bindingResult);
        try {
            String testId = testService.setNickname(testSetNicknameRequestDto);
            httpSession.setAttribute("testId", testId);
            model.addAttribute("domain-address", domainAddress);
            return "tests/create/select-question-type";
        }
        catch (NotnullException e) {
            throw new NotnullException(e.getErrors());
        }
    }

    @PostMapping("/questions")
    public String questionsAdd(@Valid QuestionDto questionDto, HttpSession httpSession) {
        String questionId = questionService.addQuestion(questionDto);
        String testId = (String) httpSession.getAttribute("testId");
        testService.addQuestionId(testId, questionId);
        return "tests/create/select-question-type";
    }

    @GetMapping("/questions/question-type/{type}")
    public String questionsTypeView(@PathVariable String type, Model model) {
        model.addAttribute("domain-address", domainAddress);
        return "tests/create/"+type;
    }

    @PostMapping("/questions/complete")
    public String questionsComplete(@Valid QuestionDto questionDto, HttpSession httpSession, Model model) {
        String questionId = questionService.addQuestion(questionDto);
        String testId = (String) httpSession.getAttribute("testId");
        model.addAttribute("domain-address", domainAddress);
        model.addAttribute("testId", testId);

        testService.addQuestionId(testId, questionId);
        testService.completeTest(testId);
        httpSession.invalidate();
        return "tests/create/complete";
    }
}