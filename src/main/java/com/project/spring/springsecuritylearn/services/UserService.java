package com.project.spring.springsecuritylearn.services;

import com.project.spring.springsecuritylearn.models.Users;
import com.project.spring.springsecuritylearn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users saveUser(Users userInput) {
        Users user = new Users();
        user.setUsername(userInput.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(userInput.getPassword()));
        return userRepository.save(user);
    }
}
