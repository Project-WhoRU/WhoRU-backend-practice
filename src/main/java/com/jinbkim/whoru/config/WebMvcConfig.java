package com.jinbkim.whoru.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("tests/create/index");
        registry.addViewController("/create/tests/init").setViewName("tests/create/set-nickname");
        registry.addViewController("/solve/tests/init").setViewName("tests/solve/set-nickname");
    }
}
