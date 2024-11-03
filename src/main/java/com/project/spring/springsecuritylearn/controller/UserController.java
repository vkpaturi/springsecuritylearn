package com.project.spring.springsecuritylearn.controller;

import com.project.spring.springsecuritylearn.models.Users;
import com.project.spring.springsecuritylearn.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<Users> saveUser(@RequestBody Users userInput) {
        System.out.println("User input: " + userInput);
        return ResponseEntity.ok(userService.saveUser(userInput));
    }
}
