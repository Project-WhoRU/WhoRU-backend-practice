package com.jinbkim.whoru.intercepter;

import com.jinbkim.whoru.config.StaticFinalString;
import com.jinbkim.whoru.contents.questionlist.domain.QuestionList;
import com.jinbkim.whoru.contents.questionlist.repository.QuestionListRepository;
import com.jinbkim.whoru.contents.users.domain.Users;
import com.jinbkim.whoru.exception.customexceptions.TestDoesntExistException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 문제지를 만들때 문제 제출 후에 뒤로가기 하여 문제지를 만들려고 할때 인터셉터
@RequiredArgsConstructor
public class CreateTestCompleteInterceptor extends HandlerInterceptorAdapter {

    private final QuestionListRepository questionListRepository;

//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Users users  = (Users)request.getSession().getAttribute("loginUser");
//        QuestionList questionList = questionListRepository.findById(users.getTestId()).orElseThrow(TestDoesntExistException::new);
//        if (questionList.getComplete() == Boolean.TRUE) {
//            response.sendRedirect("/error/404");
//            return false;
//        }
//        return true;
//    }
}
