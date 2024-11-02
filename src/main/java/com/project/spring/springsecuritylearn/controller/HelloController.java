package com.project.spring.springsecuritylearn.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("")
    public String hello(HttpServletRequest request) {
        return "Hello World!" + request.getSession().getId();
    }
}
