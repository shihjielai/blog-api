package com.springboot.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class SpringbootBlogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootBlogApiApplication.class, args);
    }

}
