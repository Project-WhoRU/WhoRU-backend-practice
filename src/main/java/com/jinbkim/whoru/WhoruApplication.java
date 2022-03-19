package com.jinbkim.whoru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource("classpath:/app.properties")
public class WhoruApplication {

    public static void main(String[] args) {
        SpringApplication.run(WhoruApplication.class, args);
    }

}
