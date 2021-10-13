package com.example.demo.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import javax.xml.crypto.Data;

@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {


    @Autowired
    private DataSource dataSource;

    //用戶設定
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String sql="SELECT username,updatepassword,enabled FROM t_user WHERE username=?";
        String authorsql="SELECT username,role FROM t_user WHERE username=?";
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(sql)
                .authoritiesByUsernameQuery(authorsql)
                .passwordEncoder(new BCryptPasswordEncoder());
    }




    @Override
    protected void configure(HttpSecurity http) throws Exception {
    http.formLogin().
            //表示要定義哪些被保護，哪些不需要保護（不需要認證）
            and().authorizeRequests()
            //.antMatchers("/**").permitAll()
            .antMatchers(HttpMethod.POST,"/register","/userlogin").permitAll()
            .antMatchers("/test").hasAuthority("ROLE_USER")
            .and()
            .csrf().disable(); //關閉csrf防護
    }

    @Bean
    PasswordEncoder password() {
        return new BCryptPasswordEncoder();
    }
}
