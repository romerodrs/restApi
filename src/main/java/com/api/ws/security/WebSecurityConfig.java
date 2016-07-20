package com.api.ws.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
/**
 * Created by DLRR
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("cloudera").password("1234").roles("USER_ADMIN");
        auth.inMemoryAuthentication().withUser("oozie").password("1234").roles("USER_OOZIE");
    }
  
    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.authorizeRequests()
      	.antMatchers("/").hasRole("USER_ADMIN")
        .antMatchers("/oozie/**").hasRole("USER_ADMIN")
        .antMatchers("/getOozieJobStatus/**").access("hasRole('USER_ADMIN') or hasRole('USER_OOZIE')")
        .anyRequest().authenticated() 
        .and().formLogin();
    }
}