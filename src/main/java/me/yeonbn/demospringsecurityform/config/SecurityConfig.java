package me.yeonbn.demospringsecurityform.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    public AccessDecisionManager accessDecisionManager() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        WebExpressionVoter webExpressionVoter = new WebExpressionVoter();
        webExpressionVoter.setExpressionHandler(handler);

        List<AccessDecisionVoter<?>> decisionVoters = Arrays.asList(webExpressionVoter);
        return new AffirmativeBased(decisionVoters);
    }

    public SecurityExpressionHandler securityExpressionHandler() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        roleHierarchy.setHierarchy("ROLE_ADMIN > ROLE_USER");

        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setRoleHierarchy(roleHierarchy);

        return handler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/", "/info*", "/account/**", "/h2-console/**").permitAll()
                .mvcMatchers("/admin*").hasRole("ADMIN")
                .mvcMatchers("/user*").hasRole("USER")
                .anyRequest().authenticated()
                .expressionHandler(securityExpressionHandler());
//                .accessDecisionManager(accessDecisionManager())

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
