package com.project.spring.springsecuritylearn.services;

import com.project.spring.springsecuritylearn.config.CustomUserDetails;
import com.project.spring.springsecuritylearn.models.Users;
import com.project.spring.springsecuritylearn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    // Provides one method to implement, we have to give spring boot the user details based on the username
    // So we use the Repository to fetch the user details from the database
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // The output of this method is a UserDetails object,
        // so we have to convert the Users object to UserDetails object
        // Again as this is a interface, we have to implement our own class --> CustomUserDetails
        return new CustomUserDetails(user);
    }
}
