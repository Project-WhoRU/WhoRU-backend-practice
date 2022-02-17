package com.jinbkim.whoru.contents.questions.controller;

import com.jinbkim.whoru.contents.questions.service.QuestionService;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.tests.service.TestService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final TestService testService;
    @Value("${domain-address}")
    private String domainAddress;

    @PostMapping
    public String questionAdd(@Valid QuestionDto questionDto, HttpSession httpSession) {
        String questionId = questionService.addQuestion(questionDto);
        String testId = (String) httpSession.getAttribute("testId");
        testService.addQuestionId(testId, questionId);
        return "redirect:/question-type";
    }

    @PostMapping("/complete")
    public String questionComplete(@Valid QuestionDto questionDto, HttpSession httpSession, Model model) {
        String questionId = questionService.addQuestion(questionDto);
        String testId = (String) httpSession.getAttribute("testId");
        model.addAttribute("domain-address", domainAddress);
        model.addAttribute("testId", testId);

        testService.addQuestionId(testId, questionId);
        testService.completeTest(testId);
        httpSession.invalidate();
        return "tests/create/complete";
    }

    @GetMapping("/question-type/{type}")
    public String questionType(@PathVariable String type, Model model) {
        model.addAttribute("domain-address", domainAddress);
        return "tests/create/"+type;
    }
}