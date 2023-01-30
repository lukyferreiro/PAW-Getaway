package ar.edu.itba.getaway.webapp.security.api.handlers;

import ar.edu.itba.getaway.webapp.dto.response.ApiErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//https://www.baeldung.com/spring-security-custom-access-denied-page
@Component
public class CustomAccessDeniedHandler implements org.springframework.security.web.access.AccessDeniedHandler {
//
//    @Autowired
//    private ObjectMapper mapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        final ApiErrorDto errorDetails = new ApiErrorDto();
        final HttpStatus status = HttpStatus.FORBIDDEN;
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setStatus(status.value());
        errorDetails.setPath(request.getRequestURI());
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(errorDetails));
    }
}
