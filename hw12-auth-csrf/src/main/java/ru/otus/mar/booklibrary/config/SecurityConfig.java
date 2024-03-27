package ru.otus.mar.booklibrary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().requireCsrfProtectionMatcher(new RequestMatcher() {
                    private RegexRequestMatcher requestMatcher = new RegexRequestMatcher("/api/.*", null);

                    @Override
                    public boolean matches(HttpServletRequest request) {
                        return requestMatcher.matches(request);
                    }
                }).csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .headers().frameOptions().sameOrigin().and()
                .authorizeHttpRequests().anyRequest().authenticated().and()
                .formLogin().loginPage("/login.html").permitAll();
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}