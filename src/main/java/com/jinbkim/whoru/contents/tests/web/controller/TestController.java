package com.jinbkim.whoru.contents.tests.web.controller;

import com.jinbkim.whoru.contents.graderesult.service.GradeResultService;
import com.jinbkim.whoru.contents.tests.web.dto.FindTestPageResponseDto;
import com.jinbkim.whoru.contents.tests.web.dto.TestSetNicknameRequestDto;
import com.jinbkim.whoru.exception.customexceptions.NotnullException;
import com.jinbkim.whoru.contents.questions.domain.question.QuestionType;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.tests.service.TestService;
import com.jinbkim.whoru.contents.tests.web.dto.TestFindResponseDto;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
public class TestController {
    private final TestService testService;
    private final GradeResultService gradeResultService;
    @Value("${domain-address}")
    private String domainAddress;

    @PostMapping("/set-nickname")
    public String setNickname(@Valid TestSetNicknameRequestDto testSetNicknameRequestDto, BindingResult bindingResult, HttpSession httpSession, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors())
            throw new NotnullException(bindingResult);
        try {
            String testId = testService.setNickname(testSetNicknameRequestDto);
            httpSession.setAttribute("testId", testId);
            redirectAttributes.addFlashAttribute("domain-address", domainAddress);
            return "redirect:/question-type";
        }
        catch (NotnullException e) {
            throw new NotnullException(e.getErrors());
        }
    }

    @GetMapping("/solve/test/start/{id}")
    public String testsSolveStart(@PathVariable String id, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        TestFindResponseDto testFindResponseDto = testService.findTest(id);
        String gradeResultId = gradeResultService.setTestId(id);

        httpSession.setAttribute("testId", id);
        httpSession.setAttribute("gradeResultId", gradeResultId);
        httpSession.setAttribute("lastPage", testFindResponseDto.getQuestions().size());

        redirectAttributes.addFlashAttribute("nickname", testFindResponseDto.getNickname());
        return "redirect:/solve/test/start";
    }

    @GetMapping("/solve/question/{page}")
    public String testsSolve(@PathVariable String page, Model model, HttpSession httpSession) {
        String testId = (String)httpSession.getAttribute("testId");
        String lastPage = String.valueOf(httpSession.getAttribute("lastPage"));
        FindTestPageResponseDto findTestPageResponseDto = testService.findTestPage(testId, page);
        QuestionDto question = findTestPageResponseDto.getQuestion();
        Boolean isLastPage = findTestPageResponseDto.getIsLastPage();

        model.addAttribute("question", question);
        model.addAttribute("page", page);
        model.addAttribute("lastPage", lastPage);
        model.addAttribute("domain-address", domainAddress);

        if (Integer.parseInt(page) != 1)  // 이전 페이지가 존재하면
            model.addAttribute("prevPage", Integer.parseInt(page)-1);
        if (isLastPage) // 마지막 페이지 이면
            model.addAttribute("isLastPage", true);
        else  // 마지막 페이지가 아니면
            model.addAttribute("nextPage", Integer.parseInt(page)+1);

        if (question.getType() == QuestionType.MULTIPLE_CHOICE)
            return "tests/solve/multiple-choice";
        else if (question.getType() == QuestionType.SHORT_ANSWER)
            return "tests/solve/short-answer";
        else if (question.getType() == QuestionType.OX)
            return "tests/solve/ox";
        return null;
    }

//    @GetMapping("/solve/test/{id}/{page}")
//    public String testsPageSolve(String testId, @PathVariable String id, @PathVariable String page, RedirectAttributes redirectAttributes) {
//        System.out.println("testid : " + testId);
//        TestFindResponseDto testFindResponseDto = testService.findTest(id);
//        redirectAttributes.addFlashAttribute("nickname", testFindResponseDto.getNickname());
//        redirectAttributes.addFlashAttribute("questions", testFindResponseDto.getQuestions());
//        return "redirect:/solve/test";
//    }
}
