package ar.edu.itba.getaway.webapp.security.api;

import ar.edu.itba.getaway.webapp.security.exceptions.ExpiredAuthTokenException;
import ar.edu.itba.getaway.webapp.security.exceptions.InvalidAuthTokenException;
import ar.edu.itba.getaway.webapp.dto.response.ApiErrorDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

//https://www.baeldung.com/spring-security-basic-authentication
public class AuthFailureMapper {

    private AuthFailureMapper() {}

    public static void handleFailure(HttpServletRequest request, HttpServletResponse response,
                                     AuthenticationException exception) throws IOException {
        ObjectMapper mapper = new ObjectMapper();

        final HttpStatus status = HttpStatus.UNAUTHORIZED;
        final ApiErrorDto errorDetails = new ApiErrorDto();

        if (exception instanceof InvalidAuthTokenException || exception instanceof InsufficientAuthenticationException) {
            response.addHeader("WWW-Authenticate", "Basic realm=\"myRealm\"");
            response.addHeader("WWW-Authenticate", "Bearer token");
        } else if (exception instanceof ExpiredAuthTokenException) {
            response.addHeader("WWW-Authenticate", "Bearer error=\"invalid_token\"");
        }

        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(exception.getMessage());
        errorDetails.setStatus(status.value());
        errorDetails.setPath(request.getRequestURI());
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_JSON);
        response.getWriter().write(mapper.writeValueAsString(errorDetails));
    }
}