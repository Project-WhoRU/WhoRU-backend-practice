package com.jinbkim.whoru.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("tests/create/index");
        registry.addViewController("/nickname").setViewName("tests/create/set-nickname");
        registry.addViewController("/question-type").setViewName("tests/create/select-question-type");
        registry.addViewController("/solve/test/start").setViewName("tests/solve/index");
        registry.addViewController("/solve/nickname").setViewName("tests/solve/set-nickname");
        registry.addViewController("/solve/grade").setViewName("tests/solve/complete");
        registry.addViewController("/solve/grade-result").setViewName("tests/solve/grade-result");

    }
}
