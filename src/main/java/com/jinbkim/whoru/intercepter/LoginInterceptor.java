package com.jinbkim.whoru.intercepter;

import com.jinbkim.whoru.config.StaticFinalString;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        if (session == null || session.getAttribute(StaticFinalString.LOGIN_USER) == null) {
            log.info("미인증 사용자 요청");
            response.sendRedirect("/users");
            return false;
        }
        return true;
    }
}
