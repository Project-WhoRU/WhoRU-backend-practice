package com.jinbkim.whoru.views.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("tests/create/index");
//        registry.addViewController("/tests/create").setViewName("tests/create/tests-create");
//        registry.addViewController("/tests/create/multiple-choice").setViewName("/tests/create/multiple-choice");
//        registry.addViewController("/tests/create/short-answer").setViewName("/tests/create/short-answer");
//        registry.addViewController("/tests/create/ox").setViewName("/tests/create/ox");
    }
}
