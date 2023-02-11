package ar.edu.itba.getaway.webapp.mappers;

import ar.edu.itba.getaway.interfaces.exceptions.CategoryNotFoundException;
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

@Provider
public class CategoryNotFoundExceptionMapper implements ExceptionMapper<CategoryNotFoundException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(CategoryNotFoundExceptionMapper.class);

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(CategoryNotFoundException e) {
        LOGGER.error("Category not found exception mapper");
        return ExceptionMapperUtil.toResponse(Response.Status.NOT_FOUND, e.getMessage(), uriInfo);
    }
}
