package ar.edu.itba.getaway.webapp.security.api;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CacheControlFilter extends OncePerRequestFilter {

    private static final int MAX_TIME = 31536000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        if (!request.getRequestURI().contains("index.html")) {
        if(request.getMethod().equals("GET")) {
            response.setHeader("Cache-Control", String.format("public, max-age=%d, inmutable", MAX_TIME));
        }
        filterChain.doFilter(request, response);
    }
}
