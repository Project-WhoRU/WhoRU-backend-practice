package com.jinbkim.whoru.contents.tests.web.controller;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.graderesult.service.GradeResultService;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.tests.domain.Tests;
import com.jinbkim.whoru.contents.tests.service.TestService;
import com.jinbkim.whoru.contents.tests.web.dto.AnswerSubmitDto;
import com.jinbkim.whoru.contents.tests.web.dto.FindTestPageResponseDto;
import com.jinbkim.whoru.contents.tests.web.dto.ReadTestByNicknameRequestDto;
import com.jinbkim.whoru.contents.tests.web.dto.TestSetNicknameRequestDto;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
//    @Value("${domain-address}")
//    private String domainAddress;

    @GetMapping("/tests/index/{nickname}")
    public String testsSolveIndexPage(@PathVariable String nickname, Model model, HttpSession httpSession) {
        Tests tests = testService.findTest(nickname);
        String gradeResultId = gradeResultService.setTestId(tests.getId());

        httpSession.setAttribute("testId", tests.getId());
        httpSession.setAttribute("gradeResultId", gradeResultId);
        httpSession.setAttribute("lastPage", tests.getQuestionIds().size());
        httpSession.setAttribute("requesterNickname", nickname);
        return "/tests/solve/index";
    }

    @GetMapping("/tests/init")
    public String testsInit(Model model) {
        model.addAttribute("nickname", new TestSetNicknameRequestDto());
        return "tests/solve/set-nickname";
    }

    @PostMapping("/nickname")
    public String responserNicknameSet(@Validated @ModelAttribute("nickname") TestSetNicknameRequestDto nickname, BindingResult bindingResult, HttpSession httpSession, RedirectAttributes redirectAttribute) {
        if (bindingResult.hasErrors())
            return "tests/solve/set-nickname";

        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");
//        redirectAttribute.addFlashAttribute("domain-address", domainAddress);

        gradeResultService.setNickname(gradeResultId, nickname.getNickname());
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
//        model.addAttribute("domain-address", domainAddress);

        if (httpSession.getAttribute("answerSubmitError") != null) {
            model.addAttribute("answerSubmitError", true);
            httpSession.removeAttribute("answerSubmitError");
        }
        model.addAttribute("answerSubmit", new AnswerSubmitDto());

        if (Integer.parseInt(page) != 1)  // 이전 페이지가 존재하면
            model.addAttribute("prevPage", Integer.parseInt(page)-1);
        if (isLastPage) // 마지막 페이지 이면
            model.addAttribute("isLastPage", true);
        else  // 마지막 페이지가 아니면
            model.addAttribute("nextPage", Integer.parseInt(page)+1);

        return "tests/solve/"+question.getType();
    }

    @PostMapping("/questions/answer")
    public String answerSubmit(String page, @Validated @ModelAttribute("answerSubmit") AnswerSubmitDto answer, BindingResult bindingResult, String button, HttpSession httpSession) {
        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");
        gradeResultService.addAnswerSubmit(gradeResultId, page, answer.getAnswer());
        int nextPage = Integer.parseInt(page)+1;
        int beforePage= Integer.parseInt(page)-1;

        if (bindingResult.hasErrors()) {
            httpSession.setAttribute("answerSubmitError", answer);
            return "redirect:/solve/questions/question-type/" + page;
        }

        if (button.equals("before"))
            return "redirect:/solve/questions/question-type/"+beforePage;
        else if (button.equals("next"))
            return "redirect:/solve/questions/question-type/"+nextPage;
        else if (button.equals("submit"))
            return "redirect:/solve/grade";
        return null;
    }

    @GetMapping("/grade")
    public String gradeTest(HttpSession httpSession, Model model, HttpServletRequest httpServletRequest) {

        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");
        String requesterNickname = (String)httpSession.getAttribute("requesterNickname");
//        String testId = (String)httpSession.getAttribute("testId");
        model.addAttribute("domain", httpServletRequest.getServerName());
//        model.addAttribute("domain-address", domainAddress);

        GradeResult gradeResult = gradeResultService.gradeTest(gradeResultId, requesterNickname);
        model.addAttribute("gradeResult", gradeResult);

//        TestFindResponseDto testFindResponseDto = testService.findTest(testId);
//        model.addAttribute("nickname", testFindResponseDto.getNickname());

        return "tests/solve/complete";
    }

    @GetMapping("/grade/{id}")
    public String gradeTestResult(@PathVariable String id, HttpSession httpSession, Model model) {
        GradeResult gradeResult = gradeResultService.findGradeResult(id);
        model.addAttribute("gradeResult", gradeResult);
        return "tests/solve/grade-result";
    }

    @PostMapping("read/nickname")
    public String readTestByNickname(ReadTestByNicknameRequestDto readTestByNicknameRequestDto) {
        String testId = this.testService.findTestIdByNickname(readTestByNicknameRequestDto.getNicknameSearch());
        if (testId == null)
            return "error/404";
        return "redirect:/solve/tests/index/" + testId;
    }
}
