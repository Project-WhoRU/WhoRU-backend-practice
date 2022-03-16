package com.jinbkim.whoru.intercepter;

import com.jinbkim.whoru.contents.questionlist.domain.QuestionList;
import com.jinbkim.whoru.contents.users.domain.Users;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

// 문제지를 만들때 문제 제출 후에 뒤로가기 하여 문제지를 만들려고 할때 인터셉터
public class CreateQuestionListCompleteInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Users users  = (Users)request.getSession().getAttribute("loginUser");
        QuestionList questionList = users.getQuestionList();
        if (questionList.isComplete() == true) {
            response.sendRedirect("/error/404");
            return false;
        }
        return true;
    }
}
