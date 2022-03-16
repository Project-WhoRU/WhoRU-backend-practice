package com.jinbkim.whoru.contents.questionlist.web.controller;
import com.jinbkim.whoru.contents.graderesult.service.GradeResultService;
import com.jinbkim.whoru.contents.questionlist.service.QuestionListService;
import com.jinbkim.whoru.contents.questions.domain.question.Question;
import com.jinbkim.whoru.contents.questions.service.QuestionService;
import com.jinbkim.whoru.contents.questionlist.repository.QuestionListRepository;
import com.jinbkim.whoru.contents.questions.web.dto.QuestionDto;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.repository.UserRepository;
import com.jinbkim.whoru.contents.users.service.UserService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping("/create-questions")
public class CreateQuestionListController {
    private final QuestionListService questionListService;
    private final QuestionService questionService;
    private final QuestionListRepository questionListRepository;
    private final QuestionValidator questionValidator;
    private final UserService userService;
    private final UserRepository userRepository;
    private final GradeResultService gradeResultService;

    @InitBinder
    public void init(WebDataBinder dataBinder) {
        dataBinder.addValidators(questionValidator);
    }

//    @Value("${domain-address}")
//    private String domainAddress;

//    @PostMapping("/nickname")
//    public String requesterNicknameSet(@Valid NicknameDto testSetNicknameRequestDto, BindingResult bindingResult, HttpSession httpSession, RedirectAttributes redirectAttributes) {
//        if (bindingResult.hasErrors())
//            throw new NotnullException(bindingResult);
//        try {
//            String testId = testService.setNickname(testSetNicknameRequestDto);
//            httpSession.setAttribute("testId", testId);
//            redirectAttributes.addAttribute("domain-address", domainAddress);
//            return "redirect:/create-questions/questions/question-type";
//        }
//        catch (NotnullException e) {
//            throw new NotnullException(e.getErrors());
//        }
//    }

    @PostMapping("/add-question")
    public String questionsAdd(@Validated @ModelAttribute("question") QuestionDto questionDto, BindingResult bindingResult, HttpSession httpSession) {
        if (bindingResult.hasErrors())
            return "contents/create-questions/"+ questionDto.getType();

//        String questionId = questionService.addQuestion(questionDto);
        Question question = questionService.createQuestion(questionDto);


        Users users = (Users) httpSession.getAttribute("loginUser");
//        questionListService.addQuestionId(users.getTestId(), questionId);
        questionListService.addQuestion(users.getQuestionList(), question);
//        httpSession.setAttribute("loginUser", users);
        return "redirect:/create-questions/question-type";
    }

    @GetMapping("/question-type/{type}")
    public String questionsTypeResolver(@PathVariable String type, Model model) {
        model.addAttribute("question", new QuestionDto());
        return "contents/create-questions/"+type;
    }

    @PostMapping("/submit-questionList")
    public String questionListComplete(@Valid @ModelAttribute("question")QuestionDto questionDto, BindingResult bindingResult, HttpSession httpSession, RedirectAttributes redirectAttributes, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors())
            return "contents/create-questions/"+ questionDto.getType();
        //        String questionId = questionService.addQuestion(questionDto);
        Question question = questionService.createQuestion(questionDto);
        Users users = (Users) httpSession.getAttribute("loginUser");
//        QuestionList questionList = users.getQuestionList();
        questionListService.questionListComplete(users.getQuestionList());
//        questionList.setComplete(Boolean.TRUE);
//        questionListRepository.save(questionList);

        questionListService.addQuestion(users.getQuestionList(), question);
//        questionListService.addQuestionId(users.getTestId(), questionId);
//        questionListService.completeTest(users.getTestId());
        userService.addUser(users);

        redirectAttributes.addAttribute("domain", httpServletRequest.getServerName());
        redirectAttributes.addAttribute("nickname", users.getNickname());
        return "redirect:/create-questions/complete";
    }

//    @PostMapping("/validate/nickname")
//    @ResponseBody
//    public String nicknameValidate(String nickname) {
//        return testService.validateNicknameDuplicate(nickname);
//    }

}