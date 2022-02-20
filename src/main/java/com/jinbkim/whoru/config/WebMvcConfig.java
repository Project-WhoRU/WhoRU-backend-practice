package com.jinbkim.whoru.config;

import com.jinbkim.whoru.contents.tests.repository.TestRepository;
import com.jinbkim.whoru.intercepter.CreateTestCompleteInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final TestRepository testRepository;

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("tests/create/index");
        registry.addViewController("/create/tests/init").setViewName("tests/create/set-nickname");
        registry.addViewController("/create/questions/question-type").setViewName("tests/create/select-question-type");
        registry.addViewController("/solve/tests/init").setViewName("tests/solve/set-nickname");
        registry.addViewController("/error/create-test-complete").setViewName("error/create-test-complete");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CreateTestCompleteInterceptor(testRepository))
            .addPathPatterns("/create/questions/**");
    }
}
