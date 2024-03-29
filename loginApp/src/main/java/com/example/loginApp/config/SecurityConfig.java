package com.example.loginApp.config;

import com.example.loginApp.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.util.logging.Logger;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private static final Logger LOGGER = Logger.getLogger(SecurityConfig.class.getName());

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
//    @Bean
//    public UserDetailsService userDetailsService(){
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("manager").password(passwordEncoder().encode("password")).roles("MANAGER").build());
//        manager.createUser(User.withUsername("user").password(passwordEncoder().encode("password")).roles("USER").build());
//        return manager;
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/h2-console/**").permitAll()
                    .antMatchers("/manager").hasRole("MANAGER")
                    .antMatchers("/user").hasRole("USER")
                    .anyRequest().authenticated()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .defaultSuccessUrl("/welcome", true)
                    .failureHandler(authenticationFailureHandler())
                    .permitAll()
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")
                .permitAll()
                .and()
                .headers().frameOptions().disable()
                .and()
                .csrf().disable()
                .userDetailsService(userDetailsService);


        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            String errorMessage = "Invalid username or password";
            response.sendRedirect("/login?error=" + errorMessage);

            LOGGER.warning("Authentication failed. Error message: " + errorMessage);
        };
    }
}
