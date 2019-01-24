package com.jd.wyjm.demo.server.web.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.jd.wyjm.remote.*",
        "com.jd.wyjm.remote.server.core",
        "com.jd.wyjm.demo.server.*",
        "com.jd.wyjm.demo.server.service.*"
})
public class WebApplication extends WebMvcAutoConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}
