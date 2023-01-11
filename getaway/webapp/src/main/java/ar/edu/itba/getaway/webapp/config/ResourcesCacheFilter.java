package ar.edu.itba.getaway.webapp.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class ResourcesCacheFilter extends OncePerRequestFilter {

    private final String MAX_TIME = "31536000";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().contains("index.html")) {
            response.addHeader("Cache-Control", "public, max-age=" + MAX_TIME + ", immutable");
        }
        filterChain.doFilter(request, response);
    }
}
