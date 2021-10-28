package com.nnk.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        //  Authentification bdd
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .passwordEncoder(passwordEncoder())
                .usersByUsernameQuery("select username, password, true from users where username=?")
                .authoritiesByUsernameQuery("select username, role from users where username=?");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        // Pages do not require login
        http.authorizeRequests().antMatchers("/", "/login", "/loginUser","/bidList").permitAll();

        // For ADMIN only.
        http.authorizeRequests().mvcMatchers(HttpMethod.GET, "user/list").hasAuthority("ADMIN");

        // Form Login config
        http.authorizeRequests().and().formLogin()//
                .and().formLogin()//
                .usernameParameter("username")//
                .passwordParameter("password")
                .defaultSuccessUrl("/loginUser");

        // Logout Config
        http.authorizeRequests().and().logout().logoutUrl("/logout").logoutSuccessUrl("/");

        // Form Token Google Config
        http.authorizeRequests().anyRequest().authenticated().and().oauth2Login().defaultSuccessUrl("/loginUser");
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}


