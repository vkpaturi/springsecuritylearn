package com.project.spring.springsecuritylearn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // This UserDetailsService is an interface,
    // to implement it so that it fetches the user details from the database
    // We have to implement our own class --> CustomUserDetailsService
    private UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig (UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    // Can be written using lambdas or normally
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(customizer -> customizer.disable()); // Disabling CSRF Protection

        // This would make sure that all the requests are authenticated,
        // i.e. the user has to be logged in to access any
        // can change the order of the requestMatchers to make sure that the most specific requestMatchers are at the top
        // and the most generic requestMatchers are at the bottom
        http.authorizeHttpRequests(
                request -> request
                        .requestMatchers("/hello").permitAll()
                        .requestMatchers("/students").authenticated()
                        .requestMatchers("/users").permitAll()
                        .requestMatchers("/csrf").permitAll()
                        .anyRequest().permitAll()

        );

        // This would enable the default login page provided by Spring Security(browser)
        //http.formLogin(Customizer.withDefaults());

        // This would enable the default HTTP Basic Authentication provided by Spring Security(for postman)
        http.httpBasic(Customizer.withDefaults());

        // We can avoid CSRF by making http stateless
        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    //  This bean is used by Spring Security to authenticate the user , this is an In memory user details manager, users are not from db
    //  instead of hard coding in application.properties, customized user details should be provided here
//    @Bean
//    public UserDetailsService userDetailsService() {
//
//        UserDetails user1 = User.withDefaultPasswordEncoder() // dont use this method in production, deprecated
//                .username("krishna")
//                .password("user123")
//                .roles("USER")
//                .build();
//
//        UserDetails user2 = User.withDefaultPasswordEncoder() // dont use this method in production, deprecated
//                .username("pratham")
//                .password("kohli123")
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user1, user2);
//    }


    // If we want to fetch the users from the database, we can use the below code,
    // we have DAO Authentication Provider as our Authentication Provider
    // It requires a UserDetailsService to fetch the user details from the database
    // It requires a PasswordEncoder to match the password from the database with the password provided by the user
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // You would need to provide the user details service, it is autowired in the constructor
        provider.setUserDetailsService(userDetailsService);

        // This doesn't encrypt the password
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());

        // This encrypts the password given by user before authentication
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }
}
