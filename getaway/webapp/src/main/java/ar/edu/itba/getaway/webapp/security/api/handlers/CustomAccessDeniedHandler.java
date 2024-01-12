package ar.edu.itba.getaway.webapp.security.api.handlers;

import ar.edu.itba.getaway.webapp.dto.response.ApiErrorDto;
import ar.edu.itba.getaway.webapp.security.api.CustomMediaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//https://www.baeldung.com/spring-security-custom-access-denied-page
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException {
        final ObjectMapper mapper = new ObjectMapper();
        final ApiErrorDto errorDetails = new ApiErrorDto();
        final HttpStatus status = HttpStatus.FORBIDDEN;
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setStatus(status.value());
        errorDetails.setPath(request.getRequestURI());
        response.setStatus(status.value());
        response.setContentType(CustomMediaType.ERROR_V1);
        response.getWriter().write(mapper.writeValueAsString(errorDetails));
    }
}
