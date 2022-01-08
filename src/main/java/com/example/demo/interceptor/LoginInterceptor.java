package com.example.demo.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
public class  LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (request.getSession().getAttribute("uid") == null) {
            System.out.println("session中的uid為null");
            response.setStatus(302);
            return false;
        }
        System.out.println("session中的uid為= " + request.getSession().getAttribute("uid"));
        return true;
    }
}

