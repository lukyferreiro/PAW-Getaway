package ar.edu.itba.getaway.webapp.mappers.util;

import ar.edu.itba.getaway.webapp.dto.response.ApiErrorDto;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

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
}
