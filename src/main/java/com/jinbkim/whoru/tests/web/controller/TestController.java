package com.jinbkim.whoru.tests.web.controller;

import com.jinbkim.whoru.exception.customexceptions.NotnullException;
import com.jinbkim.whoru.tests.service.TestService;
import com.jinbkim.whoru.tests.web.dto.TestFindResponseDto;
import com.jinbkim.whoru.tests.web.dto.TestSetNicknameRequestDto;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("/solve/{id}")
    public String testsSolveStart(@PathVariable String id, RedirectAttributes redirectAttributes) {
        TestFindResponseDto testFindResponseDto = testService.findTest(id);
        redirectAttributes.addFlashAttribute("id", id);
        redirectAttributes.addFlashAttribute("nickname", testFindResponseDto.getNickname());
        return "redirect:/solve";
    }
}
