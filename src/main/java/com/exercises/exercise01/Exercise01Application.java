package com.exercises.exercise01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class Exercise01Application {

    public static void main(String[] args) {
        Locale locale = new Locale("en");
        Locale.setDefault(locale);
        SpringApplication.run(Exercise01Application.class, args);
    }

}
