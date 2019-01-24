package com.jd.wyjm.demo.client.web.app;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages = {
        "com.jd.wyjm.remote.client.*",
        "com.jd.wyjm.demo.client.*",
        "com.jd.wyjm.demo.client.service.*"

})
@ImportResource(value = {"classpath:config/consumer.xml"})
public class WebApplication extends WebMvcAutoConfiguration {

    public static void main(String[] args) {
        SpringApplication.run(WebApplication.class, args);
    }

}