package com.jinbkim.whoru.contents.users.web.controller;

import com.jinbkim.whoru.config.StaticFinalString;
import com.jinbkim.whoru.contents.tests.domain.Tests;
import com.jinbkim.whoru.contents.tests.service.TestService;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.service.UserService;
import com.jinbkim.whoru.contents.users.web.dto.UserDto;
import com.jinbkim.whoru.validator.UserValidator;
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
    private final UserValidator userValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(userValidator);
    }

    @GetMapping
    public String setUser(Model model) {
        model.addAttribute("user", new UserDto());
        return "tests/create/set-user";
    }

    @PostMapping
    public String addUser(@Validated @ModelAttribute("user") UserDto user, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors())
            return "tests/create/set-user";

        Tests tests = this.testService.addTest();
        Users users = this.userService.addUser(user, tests);

        httpSession.setAttribute(StaticFinalString.LOGIN_USER, users);
        return "redirect:/create/questions/question-type";
    }
}
