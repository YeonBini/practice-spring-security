package me.yeonbn.demospringsecurityform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/", "/info*", "/account/**", "/h2-console/**").permitAll()
                .mvcMatchers("/admin*").hasRole("ADMIN")
                .anyRequest().authenticated();

        http.formLogin();
        http.httpBasic();

        // h2 console 확인을 위한 처리
        http.csrf().ignoringAntMatchers("/h2-console/**");
        http.headers().frameOptions().disable();
    }

    /**
     * deprecated
     */
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        // {noop} spring security 5부터 사용하는 기본 인코더
//        auth.inMemoryAuthentication()
//                .withUser("test").password("{noop}123").roles("USER").and()
//                .withUser("admin").password("{noop}admin").roles("ADMIN");
//    }
}
