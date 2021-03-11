package br.edu.ifsp.spo.bulls.users.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"br.edu.ifsp.spo.bulls.common.api.service", "br.edu.ifsp.spo.bulls.users.api.*"})
@EnableSwagger2
public class UsersApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(UsersApiApplication.class, args);
    }
}
