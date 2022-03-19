package com.jinbkim.whoru.intercepter;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import com.jinbkim.whoru.contents.utils.web.dto.Views;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

// 문제지의 답안지 제출 후에 뒤로가기 하여 문제지를 만들려고 할때 인터셉터
@Slf4j
public class solveQuestionListCompleteInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        GradeResult gradeResult = (GradeResult) request.getSession().getAttribute("gradeResult");
        if (gradeResult.isComplete() == true) {
            log.info(Views.ALREADY_SUBMITTED_GRADE_RESULT);
            response.sendRedirect("/error/404");
            return false;
        }
        return true;
    }
}
