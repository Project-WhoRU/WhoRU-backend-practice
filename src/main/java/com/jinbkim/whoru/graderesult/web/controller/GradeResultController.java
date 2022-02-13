package com.jinbkim.whoru.graderesult.web.controller;

import com.jinbkim.whoru.graderesult.domain.GradeResult;
import com.jinbkim.whoru.graderesult.service.GradeResultService;
import com.jinbkim.whoru.tests.service.TestService;
import com.jinbkim.whoru.tests.web.dto.TestFindResponseDto;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RequiredArgsConstructor
@Controller
@RequestMapping("/api/grade-result")
public class GradeResultController {
    private final GradeResultService gradeResultService;
    private final TestService testService;
    @Value("${domain-address}")
    private String domainAddress;

    @PostMapping("/set-nickname")
    public String setNickname(String nickname, HttpSession httpSession, RedirectAttributes redirectAttribute) {
        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");
        redirectAttribute.addFlashAttribute("domain-address", domainAddress);

        gradeResultService.setNickname(gradeResultId, nickname);
        return "redirect:/solve/question/1";
    }

    @PostMapping("/add-answer")
    public String answerSubmitAdd(String page, String answer, String button, HttpSession httpSession) {
        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");

        gradeResultService.addAnswerSubmit(gradeResultId, page, answer);
        int nextPage = Integer.parseInt(page)+1;
        int beforePage= Integer.parseInt(page)-1;

        if (button.equals("before"))
            return "redirect:/solve/question/"+beforePage;
        else if (button.equals("next"))
            return "redirect:/solve/question/"+nextPage;
        else if (button.equals("submit")) {
            return "redirect:/api/grade-result/solve/grade";
        }
        return null;
    }

    @GetMapping("/solve/grade")
    public String gradeTest(HttpSession httpSession, RedirectAttributes redirectAttribute) {
        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");
        String testId = (String)httpSession.getAttribute("testId");
        redirectAttribute.addFlashAttribute("domain-address", domainAddress);

        GradeResult gradeResult = gradeResultService.gradeTest(gradeResultId);
        redirectAttribute.addFlashAttribute("gradeResult", gradeResult);
        TestFindResponseDto testFindResponseDto = testService.findTest(testId);
        redirectAttribute.addFlashAttribute("nickname", testFindResponseDto.getNickname());

        return "redirect:/solve/grade";
    }

    @GetMapping("grade-result/{id}")
    public String gradeResult(@PathVariable String id, HttpSession httpSession, RedirectAttributes redirectAttribute) {
        GradeResult gradeResult = gradeResultService.findGradeResult(id);
        redirectAttribute.addFlashAttribute("gradeResult", gradeResult);

        httpSession.invalidate();
        return "redirect:/solve/grade-result";
    }
}
