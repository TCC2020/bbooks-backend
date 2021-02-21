package br.edu.ifsp.spo.bulls.feed.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableFeignClients
@EnableSwagger2
public class FeedApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeedApiApplication.class, args);
    }
}
