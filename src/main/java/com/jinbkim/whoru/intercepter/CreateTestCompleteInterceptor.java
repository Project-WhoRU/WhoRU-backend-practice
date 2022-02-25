package com.jinbkim.whoru.intercepter;

import com.jinbkim.whoru.contents.tests.domain.Tests;
import com.jinbkim.whoru.contents.tests.repository.TestRepository;
import com.jinbkim.whoru.exception.customexceptions.TestDoesntExistException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

// 문제지를 만들때 문제 제출 후에 뒤로가기 하여 문제지를 만들려고 할때 인터셉터
@RequiredArgsConstructor
public class CreateTestCompleteInterceptor extends HandlerInterceptorAdapter {

    private final TestRepository testRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String testId = (String)request.getSession().getAttribute("testId");
        Tests tests = testRepository.findById(testId).orElseThrow(TestDoesntExistException::new);
        if (tests.getComplete() == Boolean.TRUE)
            response.sendRedirect(request.getContextPath()+"/error/404");
        return true;
    }
}