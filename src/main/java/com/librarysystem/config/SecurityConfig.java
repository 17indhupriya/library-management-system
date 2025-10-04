package com.librarysystem.config;

import com.librarysystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserService userService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, HandlerMappingIntrospector introspector) throws Exception {
        MvcRequestMatcher.Builder mvcMatcherBuilder = new MvcRequestMatcher.Builder(introspector);

        http.authorizeHttpRequests(authorize -> authorize
                // Static resources and H2 console accessible to all
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                .requestMatchers(mvcMatcherBuilder.pattern("/css/**")).permitAll()
                .requestMatchers(mvcMatcherBuilder.pattern("/js/**")).permitAll()
                .requestMatchers(mvcMatcherBuilder.pattern("/images/**")).permitAll()
                // Login and registration pages accessible to all
                .requestMatchers(mvcMatcherBuilder.pattern("/login")).permitAll()
                .requestMatchers(mvcMatcherBuilder.pattern("/register")).permitAll()
                // Book management only for librarians
                .requestMatchers(mvcMatcherBuilder.pattern("/books/add")).hasRole("LIBRARIAN")
                .requestMatchers(mvcMatcherBuilder.pattern("/books/edit/**")).hasRole("LIBRARIAN")
                .requestMatchers(mvcMatcherBuilder.pattern("/books/delete/**")).hasRole("LIBRARIAN")
                .requestMatchers(mvcMatcherBuilder.pattern("/books/manage")).hasRole("LIBRARIAN")
                // Book borrowing/returning for students
                .requestMatchers(mvcMatcherBuilder.pattern("/books/borrow/**")).hasRole("STUDENT")
                .requestMatchers(mvcMatcherBuilder.pattern("/books/return/**")).hasRole("STUDENT")
                .requestMatchers(mvcMatcherBuilder.pattern("/books/borrowed")).hasRole("STUDENT")
                // Viewing books for both roles
                .requestMatchers(mvcMatcherBuilder.pattern("/books")).hasAnyRole("LIBRARIAN", "STUDENT")
                .requestMatchers(mvcMatcherBuilder.pattern("/books/search")).hasAnyRole("LIBRARIAN", "STUDENT")
                // Any other request requires authentication
                .anyRequest().authenticated())
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard")
                .permitAll())
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .permitAll())
            .exceptionHandling(handling -> handling
                .accessDeniedPage("/access-denied"))
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin()))
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")));

        return http.build();
    }
}