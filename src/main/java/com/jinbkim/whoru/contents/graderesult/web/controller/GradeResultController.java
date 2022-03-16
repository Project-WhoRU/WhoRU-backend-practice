package com.jinbkim.whoru.contents.graderesult.web.controller;

import com.jinbkim.whoru.config.StaticFinalString;
import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.graderesult.repository.GradeResultRepository;
import com.jinbkim.whoru.contents.graderesult.service.GradeResultService;
import com.jinbkim.whoru.contents.users.domain.Users;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/grade-questions")
public class GradeResultController {
    private final GradeResultRepository gradeResultRepository;
    private final GradeResultService gradeResultService;

    @GetMapping
    public String grade(HttpSession httpSession, Model model, HttpServletRequest httpServletRequest) {

//        String gradeResultId = (String)httpSession.getAttribute("gradeResultId");
        GradeResult gradeResult = (GradeResult)httpSession.getAttribute("gradeResult");
//        String requesterNickname = (String)httpSession.getAttribute("requesterNickname");
//        String testId = (String)httpSession.getAttribute("testId");
        model.addAttribute("domain", httpServletRequest.getServerName());
//        model.addAttribute("domain-address", domainAddress);

//        GradeResult gradeResult = gradeResultService.gradeTest(gradeResultId, requesterNickname);
//        GradeResult gradeResult = gradeResultService.grade(gradeResult1.getId(), requesterNickname);
        gradeResultService.grade(gradeResult);
        model.addAttribute("gradeResult", gradeResult);

//        TestFindResponseDto testFindResponseDto = testService.findTest(testId);
//        model.addAttribute("nickname", testFindResponseDto.getNickname());

        return "contents/solve-questions/complete";
    }

    @GetMapping("/page/{id}")
    public String gradeResultPage(@PathVariable String id, Model model) {
        GradeResult gradeResult = gradeResultRepository.findById(id).orElseGet(() -> null);
        model.addAttribute("gradeResult", gradeResult);
        return "contents/solve-questions/grade-result";
    }
}
