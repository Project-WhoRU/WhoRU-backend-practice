package com.jinbkim.whoru.contents.users.web.controller;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.graderesult.repository.GradeResultRepository;
import com.jinbkim.whoru.contents.graderesult.service.GradeResultService;
import com.jinbkim.whoru.contents.questionlist.domain.QuestionList;
import com.jinbkim.whoru.contents.questionlist.service.QuestionListService;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.repository.UserRepository;
import com.jinbkim.whoru.contents.users.service.UserService;
import com.jinbkim.whoru.contents.users.web.dto.LoginDto;
import com.jinbkim.whoru.contents.users.web.dto.SignUpDto;
import com.jinbkim.whoru.validator.LoginValidator;
import com.jinbkim.whoru.validator.SignUpValidator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final QuestionListService questionListService;
    private final SignUpValidator signUpValidator;
    private final UserRepository userRepository;
    private final LoginValidator loginValidator;
    private final GradeResultService gradeResultService;
    private final GradeResultRepository gradeResultRepository;


    @InitBinder("login")
    public void initLoginDto(WebDataBinder dataBinder) {
        dataBinder.addValidators(loginValidator);
    }

    @InitBinder("signUp")
    public void initSignUpDto(WebDataBinder dataBinder) {
        dataBinder.addValidators(signUpValidator);
    }

    // read
    @GetMapping("/login")
    public String login(Model model, HttpServletRequest httpServletRequest,
        HttpSession httpSession) {
        String oldURL = httpServletRequest.getHeader("Referer");
        httpSession.setAttribute("oldURL", oldURL);

        model.addAttribute("login", new LoginDto());
        return "contents/users/login";
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("signUp", new SignUpDto());
        return "contents/users/sign-up";
    }

    @GetMapping("/detail")
    public String userDetail(Model model, HttpSession httpSession,
        HttpServletRequest httpServletRequest) {
        Users users = (Users) httpSession.getAttribute("loginUser");
        if (users.getQuestionList() == null) {
            QuestionList questionList = questionListService.createQuestionList();
            userService.setQuestionList(users, questionList);
        }
        QuestionList questionList = users.completeCheckAndGetQuestionList();
        userRepository.save(users);

        if (questionList.getQuestions().size() == 0) {
            model.addAttribute("testEmpty", true);
        }
        model.addAttribute("domain", httpServletRequest.getServerName());
        model.addAttribute("nickname", users.getNickname());

        return "contents/users/user-detail";
    }

    @GetMapping("/login-resolver")
    public String loginResolver(HttpSession httpSession) {
        Users users = (Users) httpSession.getAttribute("loginUser");
        if (users == null) {
            return "redirect:/users/login";
        }

        Users Users = userRepository.findById(users.getId()).orElseGet(() -> null);
        if (Users == null) {
            return "redirect:/users/login";
        }

        return "redirect:/users/detail";
    }


    // update
    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("login") LoginDto loginDto,
        BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "contents/users/login";
        }

        Users users = userRepository.findByNickname(loginDto.getNickname());
        httpSession.setAttribute("loginUser", users);

        String oldURL = (String) httpSession.getAttribute("oldURL");
        return "redirect:" + oldURL;
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated @ModelAttribute("signUp") SignUpDto signUpDto,
        BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors()) {
            return "contents/users/sign-up";
        }

        QuestionList questionList = questionListService.createQuestionList();
        Users users = userService.createUser(signUpDto, questionList);

        httpSession.setAttribute("loginUser", users);
        return "redirect:/create-questions/question-type";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute("loginUser");
        return "redirect:/";
    }


    // delete
    @GetMapping("/delete-questionList")
    public String deleteQuestionList(HttpSession httpSession) {
        Users users = (Users) httpSession.getAttribute("loginUser");
        questionListService.deleteQuestionList(users);
        gradeResultService.deleteGradeResult(users);
        return "redirect:/users/detail";
    }

    @GetMapping("/gradeResultList")
    public String findGradeResult(Model model, HttpSession httpSession) {
        Users users = (Users) httpSession.getAttribute("loginUser");
        gradeResultService.deleteUnCompleteGradeResult(users);
        List<GradeResult> gradeResultList = gradeResultRepository.findByUsers(users);
        if (gradeResultList.size() != 0) {
            model.addAttribute("gradeResultList", gradeResultList);
        }

        return "contents/users/grade-result-list";
    }
}
