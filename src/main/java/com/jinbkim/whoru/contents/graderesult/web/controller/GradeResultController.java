package com.jinbkim.whoru.contents.graderesult.web.controller;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.graderesult.repository.GradeResultRepository;
import com.jinbkim.whoru.contents.graderesult.service.GradeResultService;
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

    // read
    @GetMapping("/page/{id}")
    public String gradeResultPage(@PathVariable String id, Model model) {
        GradeResult gradeResult = gradeResultRepository.findById(id).orElseGet(() -> null);
        model.addAttribute("gradeResult", gradeResult);
        return "contents/solve-questions/grade-result";
    }


    // update
    @GetMapping
    public String grade(HttpSession httpSession, Model model,
        HttpServletRequest httpServletRequest) {
        GradeResult gradeResult = (GradeResult) httpSession.getAttribute("gradeResult");
        gradeResultService.grade(gradeResult);

        model.addAttribute("domain", httpServletRequest.getServerName());
        model.addAttribute("gradeResult", gradeResult);
        return "contents/solve-questions/complete";
    }
}
