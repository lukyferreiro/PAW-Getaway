package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.interfaces.exceptions.CategoryNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Singleton
@Component
@Provider
public class CategoryNotFoundExceptionMapper implements ExceptionMapper<CategoryNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryNotFoundExceptionMapper.class);

    @Autowired
    private MessageSource messageSource;

    public CategoryNotFoundExceptionMapper() {
    }

    @Override
    public Response toResponse(CategoryNotFoundException ex) {
        LOGGER.error("Category not found exception mapper");
        final String message = messageSource.getMessage(ex.getMessage(), null, LocaleContextHolder.getLocale());
        return Response.status(Response.Status.NOT_FOUND).entity(message).build();
    }
}
