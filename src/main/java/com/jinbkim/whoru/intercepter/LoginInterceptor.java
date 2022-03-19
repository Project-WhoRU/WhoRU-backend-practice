package com.jinbkim.whoru.intercepter;

import com.jinbkim.whoru.contents.utils.web.dto.Views;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

// 로그인이 필요한 상황에서 로그인이 안되었을때 인터셉터
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute("loginUser") == null) {
            log.info(Views.NONE_AUTHORIZATION);
            response.sendRedirect("/users/login");
            return false;
        }
        return true;
    }
}
