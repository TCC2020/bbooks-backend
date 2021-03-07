package br.edu.ifsp.spo.bulls.competition.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"br.edu.ifsp.spo.bulls.common.api.service", "br.edu.ifsp.spo.bulls.competition.api.*"})
@EnableFeignClients
@EnableSwagger2
public class CompetitionApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(CompetitionApiApplication.class, args);
    }
}
