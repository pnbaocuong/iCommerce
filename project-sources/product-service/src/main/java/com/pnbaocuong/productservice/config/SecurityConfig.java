package com.pnbaocuong.productservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.session.ConcurrentSessionFilter;

import com.pnbaocuong.productservice.controller.ProductController;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.addFilterAfter(new CustomAuthenticationFilter(), ConcurrentSessionFilter.class);
        http.authorizeRequests()
                .antMatchers(ProductController.PRODUCT_PATH).authenticated()
                .antMatchers(ProductController.PRODUCT_PATH + "/*").authenticated()
                .anyRequest().permitAll();
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        web
                .ignoring()
                .antMatchers("/h2-console/**");
    }
}
