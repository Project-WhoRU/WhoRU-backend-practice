package com.jinbkim.whoru.contents.utils.web.controller;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Controller
@RequiredArgsConstructor
public class UtilsController {

    // update
    @GetMapping("/language")
    public String setLanguage(@RequestParam String language, HttpSession session,
        HttpServletRequest httpServletRequest) {
        session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, new Locale(language));
        String referer = "redirect:" + httpServletRequest.getHeader("referer");
        return referer;
    }
}
