package com.api.ws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("cloudera").password("1234").roles("USER_ADMIN");
        auth.inMemoryAuthentication().withUser("user").password("1234").roles("USER_BASIC");
        auth.inMemoryAuthentication().withUser("oozie").password("1234").roles("USER_OOZIE");
    }
  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
      	.antMatchers("/").hasRole("USER_ADMIN")
        .antMatchers("/oozie").access("hasRole('USER_ADMIN') or hasRole('USER_OOZIE')")
        .antMatchers("/users/**").access("hasRole('USER_ADMIN') or hasRole('USER_BASIC')")
        .antMatchers("/createUser/**").access("hasRole('USER_ADMIN') or hasRole('USER_BASIC')")
        .antMatchers("/deleteUser/**").hasRole("USER_ADMIN")
        .antMatchers("/updateUser/**").hasRole("USER_ADMIN")
        .anyRequest().authenticated() 
        .and().formLogin();
    }
}