package com.jinbkim.whoru.views.controller;

import com.jinbkim.whoru.exception.customexceptions.NotnullException;
import com.jinbkim.whoru.tests.service.TestService;
import com.jinbkim.whoru.tests.web.dto.TestSetNicknameRequestDto;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ViewController {
    private final TestService testService;

    @PostMapping("/set-nickname")
    public String setNickname(@Valid TestSetNicknameRequestDto testSetNicknameRequestDto, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors())
            throw new NotnullException(bindingResult);
        try {
            String testId = testService.setNickname(testSetNicknameRequestDto);
            httpSession.setAttribute("testId", testId);
            return "redirect:/question-type";
        }
        catch (NotnullException e) {
            throw new NotnullException(e.getErrors());
        }
    }
}
