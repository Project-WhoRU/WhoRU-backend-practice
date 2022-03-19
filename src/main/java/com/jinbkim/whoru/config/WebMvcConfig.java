package com.jinbkim.whoru.config;

import com.jinbkim.whoru.intercepter.CreateQuestionListCompleteInterceptor;
import com.jinbkim.whoru.intercepter.LoginInterceptor;
import com.jinbkim.whoru.intercepter.solveQuestionListCompleteInterceptor;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    // 해당 URL에 요청이 들어오면, 바로 html 문서를 보여줌
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("contents/create-questions/index");
        registry.addViewController("/create-questions/question-type")
            .setViewName("contents/create-questions/select-question-type");
        registry.addViewController("/error/404").setViewName("error/404");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 로그인 했는지 안했는지 확인
        registry.addInterceptor(new LoginInterceptor())
            .addPathPatterns("/create-questions/**", "/users/**")
            .excludePathPatterns("/users/login", "/users/sign-up");
        // 문제 생성 완료를 했는지 안했는지 확인
        registry.addInterceptor(new CreateQuestionListCompleteInterceptor())
            .addPathPatterns("/create-questions/**")
            .excludePathPatterns("/create-questions/complete");
        // 문제 풀기 완료를 했는지 안했는지 확인
        registry.addInterceptor(new solveQuestionListCompleteInterceptor())
            .addPathPatterns("/solve-questions/**")
            .excludePathPatterns("/solve-questions/index/**", "/solve-questions/search");
    }

    // 언어 설정
    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return sessionLocaleResolver;
    }
}
