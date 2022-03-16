package com.jinbkim.whoru.config;

import com.jinbkim.whoru.contents.questionlist.repository.QuestionListRepository;
import com.jinbkim.whoru.intercepter.CreateTestCompleteInterceptor;
import com.jinbkim.whoru.intercepter.LoginInterceptor;
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

    private final QuestionListRepository questionListRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("contents/create-questions/index");
        registry.addViewController("/create-questions/question-type").setViewName("contents/create-questions/select-question-type");
        registry.addViewController("/error/404").setViewName("error/404");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            .addPathPatterns("/create-questions/**", "/users/**")
            .excludePathPatterns("/users/login", "/users/sign-up");

//        registry.addInterceptor(new CreateTestCompleteInterceptor(questionListRepository))
//            .addPathPatterns("/create-questions/**")
//            .excludePathPatterns("/create-questions/questions/complete", "/create-questions/tests/delete", "/create-questions/tests");
    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return sessionLocaleResolver;
    }
}
