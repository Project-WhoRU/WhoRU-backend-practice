package com.jinbkim.whoru.intercepter;

import com.jinbkim.whoru.contents.graderesult.domain.GradeResult;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

public class solveQuestionListCompleteInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        GradeResult gradeResult = (GradeResult)request.getSession().getAttribute("gradeResult");
        if (gradeResult.isComplete() == true) {
            response.sendRedirect("/error/404");
            return false;
        }
        return true;
    }
}
