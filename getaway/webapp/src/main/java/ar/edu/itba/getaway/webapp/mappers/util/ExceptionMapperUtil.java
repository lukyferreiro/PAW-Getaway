package ar.edu.itba.getaway.webapp.mappers.util;

import ar.edu.itba.getaway.webapp.dto.response.ApiErrorDto;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.Locale;

public class ExceptionMapperUtil {

    private ExceptionMapperUtil() {}

    public static Response toResponse(Response.Status status, String message, UriInfo uriInfo) {
        final ApiErrorDto errorDetails = new ApiErrorDto();
        errorDetails.setStatus(status.getStatusCode());
        errorDetails.setTitle(status.getReasonPhrase());
        errorDetails.setMessage(message);
        errorDetails.setPath(uriInfo.getAbsolutePath().getPath());
        return Response.status(status).entity(errorDetails).type(MediaType.APPLICATION_JSON).build();
    }

    public static String getLocalizedMessage(String key, MessageSource messageSource) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(key, null, locale);
    }
}
