package com.example.demo.config;

import com.example.demo.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {

/*
        //在這個patterns裡面就是不用登入的
        List<String> patterns = new ArrayList<>();
        patterns.add("/user_login");
        patterns.add("/static/**");
*/

        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/login_success")
                .addPathPatterns("/carts/**")
                .addPathPatterns("/users/**");
        //.excludePathPatterns(patterns);
    }
}
