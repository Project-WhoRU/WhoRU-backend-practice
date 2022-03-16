package com.jinbkim.whoru.contents.questionlist.web.controller;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.graderesult.service.GradeResultService;
import com.jinbkim.whoru.contents.questions.domain.question.Question;
import com.jinbkim.whoru.contents.questionlist.domain.QuestionList;
import com.jinbkim.whoru.contents.questionlist.service.QuestionListService;
import com.jinbkim.whoru.contents.questionlist.web.dto.AnswerSubmitDto;
import com.jinbkim.whoru.contents.questionlist.web.dto.ReadTestByNicknameRequestDto;
import com.jinbkim.whoru.contents.questionlist.web.dto.TestSetNicknameRequestDto;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.contents.users.repository.UserRepository;
import com.jinbkim.whoru.contents.users.service.UserService;
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
    private final UserService userService;
    private final UserRepository userRepository;

    @GetMapping("/index/{nickname}")
    public String index(@PathVariable String nickname, HttpSession httpSession) {
        Users Users = userService.findUser(nickname);

        GradeResult gradeResult = gradeResultService.createGradeResult();
        gradeResultService.setUser(gradeResult, Users);

//        QuestionList questionList = questionListService.findTest(nickname);
//        String gradeResultId = gradeResultService.setTestId(questionList.getId());
//        httpSession.setAttribute("testId", questionList.getId());
//        httpSession.setAttribute("questionListId", Users.getQuestionListId());

//        httpSession.setAttribute("gradeResultId", gradeResultId);
        httpSession.setAttribute("gradeResult", gradeResult);

//        httpSession.setAttribute("lastPage", questionList.getQuestionIds().size());
//        httpSession.setAttribute("requesterNickname", nickname);
        return "/contents/solve-questions/index";
    }

    @GetMapping("/init")
    public String init(Model model) {
        model.addAttribute("nickname", new TestSetNicknameRequestDto());
        return "contents/solve-questions/set-nickname";
    }

    @PostMapping("/set-nickname")
    public String setNickname(@Validated @ModelAttribute("nickname") TestSetNicknameRequestDto nickname, BindingResult bindingResult, HttpSession httpSession, RedirectAttributes redirectAttribute) {
        if (bindingResult.hasErrors())
            return "contents/solve-questions/set-nickname";

//        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");
        GradeResult gradeResult = (GradeResult)httpSession.getAttribute("gradeResult");

//        redirectAttribute.addFlashAttribute("domain-address", domainAddress);


//        gradeResultService.setNickname(gradeResultId, nickname.getNickname());
        gradeResultService.setNickname(gradeResult, nickname.getNickname());
        return "redirect:/solve-questions/page/1";
    }

    @GetMapping("/page/{page}")
    public String questionsSolve(@PathVariable String page, Model model, HttpSession httpSession) {
//        String testId = (String)httpSession.getAttribute("testId");
//        String testId = (String)httpSession.getAttribute("questionListId");
//        String lastPage = String.valueOf(httpSession.getAttribute("lastPage"));
        GradeResult gradeResult = (GradeResult)httpSession.getAttribute("gradeResult");
        String lastPage = Integer.toString(gradeResult.getQuestionsCount());

//        FindTestPageResponseDto findTestPageResponseDto = questionListService.findTestPage(testId, page);

        Users users = gradeResult.getUsers();
        QuestionList questionList = users.getQuestionList();
        Question question = questionList.getQuestions().get(Integer.parseInt(page)-1);
        boolean isLastPage = false;
        if (Integer.parseInt(page) == questionList.getQuestions().size())
            isLastPage = true;

//        QuestionDto question = findTestPageResponseDto.getQuestion();
//        Boolean isLastPage = findTestPageResponseDto.getIsLastPage();

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

        return "contents/solve-questions/"+question.getType();
    }

    @PostMapping("/submit-answer")
    public String answerSubmit(String page, @Validated @ModelAttribute("answerSubmit") AnswerSubmitDto answer, BindingResult bindingResult, String button, HttpSession httpSession) {
//        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");
        GradeResult gradeResult = (GradeResult)httpSession.getAttribute("gradeResult");
//        gradeResultService.addAnswerSubmit(gradeResultId, page, answer.getAnswer());
//        gradeResultService.addAnswerSubmit(gradeResult.getId(), page, answer.getAnswer());
        gradeResultService.answerSubmit(gradeResult, page, answer.getAnswer());
        int nextPage = Integer.parseInt(page)+1;
        int beforePage= Integer.parseInt(page)-1;

        if (bindingResult.hasErrors()) {
            httpSession.setAttribute("answerSubmitError", answer);
            return "redirect:/solve-questions/page/" + page;
        }

        if (button.equals("before"))
            return "redirect:/solve-questions/page/"+beforePage;
        else if (button.equals("next"))
            return "redirect:/solve-questions/page/"+nextPage;
        else if (button.equals("submit"))
            return "redirect:/grade-questions";
        return null;
    }

    @PostMapping("/search")
    public String readTestByNickname(ReadTestByNicknameRequestDto readTestByNicknameRequestDto) {
        Users user = userRepository.findByNickname(readTestByNicknameRequestDto.getNicknameSearch());
        if (user == null)
            return "error/404";
        QuestionList questionList = user.getQuestionList();

//        QuestionList questionList = questionListRepository.findByNickname
//        QuestionList test = this.questionListService.findTest(readTestByNicknameRequestDto.getNicknameSearch());
        if (questionList == null || questionList.getQuestions().size() == 0) {
            return "error/404";
        }
        return "redirect:/solve-questions/index/" + readTestByNicknameRequestDto.getNicknameSearch();
    }

}
