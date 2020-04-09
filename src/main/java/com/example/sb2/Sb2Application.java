package com.example.sb2;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@MapperScan("com.example.sb2.mapper")
@SpringBootApplication
@ImportResource(locations={"classpath:mykaptcha/mykaptcha.xml"})
public class Sb2Application {

    public static void main(String[] args) {
        SpringApplication.run(Sb2Application.class, args);
    }

}
