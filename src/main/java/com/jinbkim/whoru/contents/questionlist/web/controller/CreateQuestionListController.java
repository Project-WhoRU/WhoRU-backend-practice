package com.jinbkim.whoru.contents.questionlist.web.controller;

import com.jinbkim.whoru.contents.questionlist.domain.Question;
import com.jinbkim.whoru.contents.questionlist.service.QuestionListService;
import com.jinbkim.whoru.contents.questionlist.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.service.UserService;
import com.jinbkim.whoru.validator.QuestionValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/create-questions")
public class CreateQuestionListController {

    private final QuestionListService questionListService;
    private final QuestionValidator questionValidator;
    private final UserService userService;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(questionValidator);
    }

    // read
    @GetMapping("/question-type/{type}")
    public String questionsTypeResolver(@PathVariable String type, Model model) {
        model.addAttribute("question", new QuestionDto());
        return "contents/create-questions/" + type;
    }

    @GetMapping("/complete")
    public String questionListComplete(HttpServletRequest httpServletRequest,
        HttpSession httpSession, Model model) {
        Users users = (Users) httpSession.getAttribute("loginUser");
        model.addAttribute("domain", httpServletRequest.getServerName());
        model.addAttribute("nickname", users.getNickname());
        return "contents/create-questions/complete";
    }


    // update
    @PostMapping("/add-question")
    public String addQuestion(@Validated @ModelAttribute("question") QuestionDto questionDto,
        BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "contents/create-questions/" + questionDto.getType();
        }

        Question question = questionListService.createQuestion(questionDto);
        Users users = (Users) httpSession.getAttribute("loginUser");
        questionListService.addQuestion(users.getQuestionList(), question);

        if (questionDto.isComplete() == false) // 문제를 더 만들기
        {
            return "redirect:/create-questions/question-type";
        }
        // 문제 만들기 완성
        userService.questionListComplete(users);
        return "redirect:/create-questions/complete";
    }
}