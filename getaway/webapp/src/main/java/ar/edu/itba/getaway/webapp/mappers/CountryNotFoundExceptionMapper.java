package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.interfaces.exceptions.CountryNotFoundException;
import ar.edu.itba.getaway.webapp.mappers.util.ExceptionMapperUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class CountryNotFoundExceptionMapper implements ExceptionMapper<CountryNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountryNotFoundExceptionMapper.class);

    @Autowired
    private MessageSource messageSource;

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(CountryNotFoundException e) {
        LOGGER.error("Country expected exception mapper");
        final String message = messageSource.getMessage(e.getMessage(), null, LocaleContextHolder.getLocale());
        return ExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, message, uriInfo);
    }

}