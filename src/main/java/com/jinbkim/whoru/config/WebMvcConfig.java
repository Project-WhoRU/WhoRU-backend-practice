package com.jinbkim.whoru.config;

import com.jinbkim.whoru.contents.tests.repository.TestRepository;
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

    private final TestRepository testRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("tests/create/index");
        registry.addViewController("/create/questions/question-type").setViewName("tests/create/select-question-type");
        registry.addViewController("/error/404").setViewName("error/404");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
            .addPathPatterns("/create/**");
        registry.addInterceptor(new CreateTestCompleteInterceptor(testRepository))
            .addPathPatterns("/create/**")
            .excludePathPatterns("create/questions/complete");
    }

    @Bean
    public SessionLocaleResolver localeResolver() {
        SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
        sessionLocaleResolver.setDefaultLocale(Locale.ENGLISH);
        return sessionLocaleResolver;
    }
}
