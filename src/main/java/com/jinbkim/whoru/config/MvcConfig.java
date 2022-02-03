package com.jinbkim.whoru.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/init").setViewName("init");
        registry.addViewController("/questiontypes").setViewName("questiontypes");
        registry.addViewController("/multiplechoicequestion").setViewName("multiplechoicequestion");
        registry.addViewController("/ox").setViewName("ox");
        registry.addViewController("/shortanswer").setViewName("shortanswer");
        registry.addViewController("/completed").setViewName("completed");
    }
}

