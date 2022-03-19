package com.jinbkim.whoru.contents.questionlist.web.controller;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.graderesult.service.GradeResultService;
import com.jinbkim.whoru.contents.questionlist.domain.QuestionList;
import com.jinbkim.whoru.contents.questionlist.service.QuestionListService;
import com.jinbkim.whoru.contents.questionlist.web.dto.AnswerDto;
import com.jinbkim.whoru.contents.questionlist.web.dto.NicknameDto;
import com.jinbkim.whoru.contents.questionlist.web.dto.QuestionSolveDto;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.repository.UserRepository;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
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
@RequestMapping("/solve-questions")
public class SolveQuestionListController {

    private final QuestionListService questionListService;
    private final GradeResultService gradeResultService;
    private final UserRepository userRepository;

    // read
    @GetMapping("/index/{nickname}")
    public String index(@PathVariable String nickname, HttpSession httpSession) {
        Users users = userRepository.findByNickname(nickname);
        if (users == null) {
            System.out.println("hihi");
            return "error/404";
        }
        GradeResult gradeResult = gradeResultService.createGradeResult();
        gradeResultService.setUser(gradeResult, users);
        httpSession.setAttribute("gradeResult", gradeResult);
        return "/contents/solve-questions/index";
    }

    @GetMapping("/init")
    public String init(Model model) {
        model.addAttribute("nickname", new NicknameDto());
        return "contents/solve-questions/set-nickname";
    }

    @GetMapping("/page/{currentPage}")
    public String findQuestionsSolvePage(@PathVariable Integer currentPage, Model model,
        HttpSession httpSession) {
        GradeResult gradeResult = (GradeResult) httpSession.getAttribute("gradeResult");
        QuestionSolveDto questionSolveDto = questionListService.createQuestionSolveDto(currentPage,
            gradeResult);

        model.addAttribute("questionSolveDto", questionSolveDto);
        if (httpSession.getAttribute("answerSubmitError") != null) {  // 단답형 문제에서 에러가 생길때
            model.addAttribute("answerSubmitError", true);
            httpSession.removeAttribute("answerSubmitError");
        }
        model.addAttribute("answerSubmit", new AnswerDto());

        String questionType = questionSolveDto.getQuestion().getType();
        return "contents/solve-questions/" + questionType;
    }

    @PostMapping("/search")
    public String readQuestionListByNickname(NicknameDto nicknameDto) {
        Users user = userRepository.findByNickname(nicknameDto.getNickname());
        if (user == null)  // 검색한 유저가 없을때
        {
            return "error/404";
        }

        QuestionList questionList = user.getQuestionList();
        if (questionList == null || questionList.getQuestions().size() == 0)  //  해당 유저가 만든 문제가 없을때
        {
            return "error/404";
        }

        return "redirect:/solve-questions/index/" + nicknameDto.getNickname();
    }


    // update
    @PostMapping("/set-nickname")
    public String setNickname(@Validated @ModelAttribute("nickname") NicknameDto nickname,
        BindingResult bindingResult, HttpSession httpSession,
        RedirectAttributes redirectAttribute) {
        if (bindingResult.hasErrors()) {
            return "contents/solve-questions/set-nickname";
        }

        GradeResult gradeResult = (GradeResult) httpSession.getAttribute("gradeResult");
        gradeResultService.setNickname(gradeResult, nickname.getNickname());
        return "redirect:/solve-questions/page/1";
    }

    @PostMapping("/submit-answer")
    public String submitAnswer(Integer page,
        @Validated @ModelAttribute("answerSubmit") AnswerDto answer, BindingResult bindingResult,
        String button, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            httpSession.setAttribute("answerSubmitError", answer);
            return "redirect:/solve-questions/page/" + page;
        }

        GradeResult gradeResult = (GradeResult) httpSession.getAttribute("gradeResult");
        gradeResultService.answerSubmit(gradeResult, page, answer.getAnswer());

        if (button.equals("before"))  // 이전문제로 돌아갈때
        {
            return "redirect:/solve-questions/page/" + (page - 1);
        } else if (button.equals("next"))  // 다음문제를 풀러갈때
        {
            return "redirect:/solve-questions/page/" + (page + 1);
        } else if (button.equals("submit"))  // 답안지 제출할때
        {
            return "redirect:/grade-questions";
        }
        return null;
    }
}
