package com.jinbkim.whoru.contents.users.web.controller;

import com.jinbkim.whoru.config.StaticFinalString;
import com.jinbkim.whoru.contents.tests.domain.Tests;
import com.jinbkim.whoru.contents.tests.repository.TestRepository;
import com.jinbkim.whoru.contents.tests.service.TestService;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.domain.UsersImplement;
import com.jinbkim.whoru.contents.users.repository.UserRepository;
import com.jinbkim.whoru.contents.users.service.UserService;
import com.jinbkim.whoru.contents.users.web.dto.LoginDto;
import com.jinbkim.whoru.contents.users.web.dto.SignUpDto;
import com.jinbkim.whoru.exception.customexceptions.TestDoesntExistException;
import com.jinbkim.whoru.validator.LoginValidator;
import com.jinbkim.whoru.validator.SignUpValidator;
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
    private final TestService testService;
    private final SignUpValidator signUpValidator;
    private final UserRepository userRepository;
    private final LoginValidator loginValidator;
    private final TestRepository testRepository;

    @InitBinder("login")
    public void initLoginDto(WebDataBinder dataBinder) {
        dataBinder.addValidators(loginValidator);
    }

    @InitBinder("signUp")
    public void initSignUpDto(WebDataBinder dataBinder) {
        dataBinder.addValidators(signUpValidator);
    }

    @GetMapping("/login")
    public String login(Model model, HttpServletRequest httpServletRequest, HttpSession httpSession) {
        model.addAttribute("login", new LoginDto());
        String oldURL = httpServletRequest.getHeader("Referer");
        httpSession.setAttribute("oldURL", oldURL);
        return "tests/create/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute("login") LoginDto loginDto, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors())
            return "tests/create/login";
        UsersImplement users = userRepository.findByNickname(loginDto.getNickname());
        httpSession.setAttribute(StaticFinalString.LOGIN_USER, users);

        String oldURL = (String)httpSession.getAttribute("oldURL");
        return "redirect:"+oldURL;
    }

    @GetMapping("/sign-up")
    public String signUp(Model model) {
        model.addAttribute("signUp", new SignUpDto());
        return "tests/create/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@Validated @ModelAttribute("signUp") SignUpDto signUpDto , BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors())
            return "tests/create/sign-up";

        Tests tests = this.testService.addTest();
        Users users = this.userService.addUserBucket(signUpDto, tests);

        httpSession.setAttribute(StaticFinalString.LOGIN_USER, users);
        return "redirect:/create/questions/question-type";
    }

    @GetMapping("/detail")
    public String userDetail(Model model, HttpSession httpSession, HttpServletRequest httpServletRequest) {
        UsersImplement users = (UsersImplement) httpSession.getAttribute(StaticFinalString.LOGIN_USER);
        if (users.getTestId() == null) {
            Tests tests = this.testService.addTest();
            users.setTestId(tests.getId());
            this.userRepository.save(users);
        }
        httpSession.setAttribute(StaticFinalString.LOGIN_USER, users);

        Tests tests = this.testRepository.findById(users.getTestId()).orElseThrow(TestDoesntExistException::new);
        if (tests.getQuestionIds().size() == 0) {
            model.addAttribute("testEmpty", true);
        }

        model.addAttribute("domain", httpServletRequest.getServerName());
        model.addAttribute("nickname", users.getNickname());

        return "tests/create/user-detail";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {
        httpSession.removeAttribute(StaticFinalString.LOGIN_USER);
        return "redirect:/";
    }
}
