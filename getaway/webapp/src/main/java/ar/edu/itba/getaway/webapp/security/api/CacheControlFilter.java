package ar.edu.itba.getaway.webapp.security.api;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


//https://www.baeldung.com/cachable-static-assets-with-spring-mvc
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
