package com.jinbkim.whoru.contents.tests.web.controller;

import com.jinbkim.whoru.config.StaticFinalString;
import com.jinbkim.whoru.contents.questions.service.QuestionService;
import com.jinbkim.whoru.contents.tests.domain.Tests;
import com.jinbkim.whoru.contents.tests.repository.TestRepository;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.tests.service.TestService;
import com.jinbkim.whoru.exception.customexceptions.TestDoesntExistException;
import com.jinbkim.whoru.validator.QuestionValidator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
@RequestMapping("/create")
public class CreateTestController {
    private final TestService testService;
    private final QuestionService questionService;
    private final TestRepository testRepository;
    private final QuestionValidator questionValidator;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(questionValidator);
    }

//    @Value("${domain-address}")
//    private String domainAddress;

//    @PostMapping("/nickname")
//    public String requesterNicknameSet(@Valid TestSetNicknameRequestDto testSetNicknameRequestDto, BindingResult bindingResult, HttpSession httpSession, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors())
//            throw new NotnullException(bindingResult);
//        try {
//            String testId = testService.setNickname(testSetNicknameRequestDto);
//            httpSession.setAttribute("testId", testId);
//            redirectAttributes.addAttribute("domain-address", domainAddress);
//            return "redirect:/create/questions/question-type";
//        }
//        catch (NotnullException e) {
//            throw new NotnullException(e.getErrors());
//        }
//    }

    @PostMapping("/questions")
    public String questionsAdd(@Validated @ModelAttribute("question") QuestionDto questionDto, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors())
            return "tests/create/" + questionDto.getType();

        String questionId = questionService.addQuestion(questionDto);
        Users users = (Users) httpSession.getAttribute(StaticFinalString.LOGIN_USER);
        testService.addQuestionId(users.getTestId(), questionId);
        return "redirect:/create/questions/question-type";
    }

    @GetMapping("/questions/question-type/{type}")
    public String questionsTypeView(@PathVariable String type, Model model) {
        model.addAttribute("question", new QuestionDto());
//        model.addAttribute("domain-address", domainAddress);
        return "tests/create/"+type;
    }

    @PostMapping("/questions/complete")
    public String questionsComplete(@Valid QuestionDto questionDto, HttpSession httpSession, Model model, HttpServletRequest httpServletRequest) {
        String questionId = questionService.addQuestion(questionDto);
        Users users = (Users) httpSession.getAttribute(StaticFinalString.LOGIN_USER);
        Tests tests = testRepository.findById(users.getTestId()).orElseThrow(TestDoesntExistException::new);
        tests.setComplete(Boolean.TRUE);
        testRepository.save(tests);

        model.addAttribute("domain", httpServletRequest.getServerName());
        model.addAttribute("nickname", users.getNickname());

        testService.addQuestionId(users.getTestId(), questionId);
        testService.completeTest(users.getTestId());
        return "tests/create/complete";
    }

    @PostMapping("/validate/nickname")
    @ResponseBody
    public String nicknameValidate(String nickname) {
        return testService.validateNicknameDuplicate(nickname);
    }
}