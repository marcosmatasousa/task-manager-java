//package com.barber.project.filters;
//import jakarta.servlet.*;
//import jakarta.servlet.FilterConfig;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.stereotype.Component;
//import java.io.IOException;
//
//@Component
//public class DemoFilter implements Filter {
//
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        Filter.super.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest,
//                         ServletResponse servletResponse,
//                         FilterChain filterChain) throws IOException, ServletException {
//
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        if (req.getMethod().equalsIgnoreCase("POST") && req.getRequestURI().equals("/users")) {
//            System.out.println("passou aqui");
//        }
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//    @Override
//    public void destroy() {
//        Filter.super.destroy();
//    }
//}
