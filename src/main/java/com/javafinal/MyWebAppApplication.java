package com.javafinal;

import com.javafinal.Model.User;
import com.javafinal.Model.UserRepository;
import com.javafinal.Service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MyWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyWebAppApplication.class, args);
    }

}
