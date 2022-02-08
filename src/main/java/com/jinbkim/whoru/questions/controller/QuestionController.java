package com.jinbkim.whoru.questions.controller;

import com.jinbkim.whoru.questions.service.QuestionService;
import com.jinbkim.whoru.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.tests.service.TestService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/questions")
public class QuestionController {

    private final QuestionService questionService;
    private final TestService testService;

    @PostMapping
    public String questionAdd(@Valid QuestionDto questionDto, HttpSession httpSession) {
        String questionId = questionService.addQuestion(questionDto);
        String testId = (String) httpSession.getAttribute("testId");
        testService.addQuestionId(testId, questionId);
        return "redirect:/question-type";
    }

    @PostMapping("/complete")
    public String questionComplete(@Valid QuestionDto questionDto, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        String questionId = questionService.addQuestion(questionDto);
        String testId = (String) httpSession.getAttribute("testId");
        testService.addQuestionId(testId, questionId);

        testService.completeTest(testId);
        httpSession.invalidate();
        redirectAttributes.addFlashAttribute("testId", testId);
        return "redirect:/complete";
    }
}