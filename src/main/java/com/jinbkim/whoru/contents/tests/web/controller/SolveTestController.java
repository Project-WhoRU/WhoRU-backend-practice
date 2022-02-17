package com.jinbkim.whoru.contents.tests.web.controller;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.graderesult.service.GradeResultService;
import com.jinbkim.whoru.contents.questions.domain.question.QuestionType;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.tests.service.TestService;
import com.jinbkim.whoru.contents.tests.web.dto.FindTestPageResponseDto;
import com.jinbkim.whoru.contents.tests.web.dto.TestFindResponseDto;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/solve")
public class SolveTestController {
    private final TestService testService;
    private final GradeResultService gradeResultService;
    @Value("${domain-address}")
    private String domainAddress;

    @GetMapping("/tests/index/{id}")
    public String testsSolveIndexPage(@PathVariable String id, Model model, HttpSession httpSession) {
        TestFindResponseDto testFindResponseDto = testService.findTest(id);
        String gradeResultId = gradeResultService.setTestId(id);

        httpSession.setAttribute("testId", id);
        httpSession.setAttribute("gradeResultId", gradeResultId);
        httpSession.setAttribute("lastPage", testFindResponseDto.getQuestions().size());

        model.addAttribute("nickname", testFindResponseDto.getNickname());
        return "/tests/solve/index";
    }

    @PostMapping("/nickname")
    public String responserNicknameSet(String nickname, HttpSession httpSession, RedirectAttributes redirectAttribute) {
        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");
        redirectAttribute.addFlashAttribute("domain-address", domainAddress);

        gradeResultService.setNickname(gradeResultId, nickname);
        return "redirect:/solve/questions/question-type/1";
    }

    @GetMapping("/questions/question-type/{page}")
    public String questionsSolve(@PathVariable String page, Model model, HttpSession httpSession) {
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

    @PostMapping("/questions/answer")
    public String answerSubmit(String page, String answer, String button, HttpSession httpSession) {
        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");

        gradeResultService.addAnswerSubmit(gradeResultId, page, answer);
        int nextPage = Integer.parseInt(page)+1;
        int beforePage= Integer.parseInt(page)-1;

        if (button.equals("before"))
            return "redirect:/solve/questions/question-type/"+beforePage;
        else if (button.equals("next"))
            return "redirect:/solve/questions/question-type/"+nextPage;
        else if (button.equals("submit"))
            return "redirect:/solve/grade";
        return null;
    }

    @GetMapping("/grade")
    public String gradeTest(HttpSession httpSession, Model model) {
        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");
        String testId = (String)httpSession.getAttribute("testId");
        model.addAttribute("domain-address", domainAddress);

        GradeResult gradeResult = gradeResultService.gradeTest(gradeResultId);
        model.addAttribute("gradeResult", gradeResult);
        TestFindResponseDto testFindResponseDto = testService.findTest(testId);
        model.addAttribute("nickname", testFindResponseDto.getNickname());

        return "tests/solve/complete";
    }

    @GetMapping("/grade/{id}")
    public String gradeTestResult(@PathVariable String id, HttpSession httpSession, Model model) {
        GradeResult gradeResult = gradeResultService.findGradeResult(id);
        model.addAttribute("gradeResult", gradeResult);

        httpSession.invalidate();
        return "tests/solve/grade-result";
    }
}
