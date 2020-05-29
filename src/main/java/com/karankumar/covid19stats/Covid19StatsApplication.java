package com.karankumar.covid19stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class Covid19StatsApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(Covid19StatsApplication.class, args);
    }

}
