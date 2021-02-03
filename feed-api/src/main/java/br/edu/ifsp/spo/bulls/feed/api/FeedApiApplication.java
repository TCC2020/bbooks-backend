package br.edu.ifsp.spo.bulls.feed.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableFeignClients
@ComponentScan("br.edu.ifsp.spo.bulls.common.api.feign")
public class FeedApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(FeedApiApplication.class, args);
    }
}
