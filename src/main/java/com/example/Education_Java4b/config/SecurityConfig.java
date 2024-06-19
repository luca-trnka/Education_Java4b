package com.example.Education_Java4b.config;

import com.example.Education_Java4b.config.jwt.JwtAuthenticationEntryPoint;
import com.example.Education_Java4b.config.jwt.JwtAuthenticationFilter;
import com.example.Education_Java4b.config.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Autowired
    public SecurityConfig(JwtTokenProvider jwtTokenProvider, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    }

    @Autowired
    private CorsFilter corsFilter;

    public JwtAuthenticationFilter jwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        return new JwtAuthenticationFilter(authenticationManager, jwtTokenProvider);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                // Define your access rules here
                .antMatchers("/api/authenticate").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/users/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .addFilterBefore(jwtAuthenticationFilter(authenticationManagerBean()), UsernamePasswordAuthenticationFilter.class);
    }
}

