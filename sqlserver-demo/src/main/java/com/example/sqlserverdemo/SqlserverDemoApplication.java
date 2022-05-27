package com.example.sqlserverdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.example.sqlserverdemo.dao")
public class SqlserverDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlserverDemoApplication.class, args);
    }

}
