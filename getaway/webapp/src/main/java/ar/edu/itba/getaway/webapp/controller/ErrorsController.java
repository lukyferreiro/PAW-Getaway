package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Locale;

@ControllerAdvice
public class ErrorsController {

    public static final String ERROR_VIEW = "errors";

    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorsController.class);
    @Autowired
    private MessageSource messageSource;

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ExperienceNotFoundException.class)
    public ModelAndView experienceNotFound() {
        LOGGER.error("Error experienceNotFound caught");
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.Experience", null, locale);
        Long code = Long.valueOf(HttpStatus.NOT_FOUND.toString());
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = CategoryNotFoundException.class)
    public ModelAndView categoryNotFound() {
        LOGGER.error("Error categoryNotFound caught");
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.Category", null, locale);
        Long code = Long.valueOf(HttpStatus.NOT_FOUND.toString());
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ImageNotFoundException.class)
    public ModelAndView imageNotFound() {
        LOGGER.error("Error imageNotFound caught");
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.image", null, locale);
        Long code = Long.valueOf(HttpStatus.NOT_FOUND.toString());
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = UserNotFoundException.class)
    public ModelAndView userNotFound() {
        LOGGER.error("Error userNotFound caught");
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.user", null, locale);
        Long code = Long.valueOf(HttpStatus.NOT_FOUND.toString());
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = IllegalContentTypeException.class)
    public ModelAndView illegalContentTypeException() {
        LOGGER.error("Error illegalContentTypeException caught");
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.IllegalContentTypeException", null, locale);
        Long code = Long.valueOf(HttpStatus.BAD_REQUEST.toString());
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = MultipartException.class)
    public ModelAndView maxUploadSizeException() {
        LOGGER.error("Error maxUploadSizeException caught");
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.MaxUploadSizeException", null, locale);
        Long code = Long.valueOf(HttpStatus.BAD_REQUEST.toString());
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);

        return mav;
    }

    /*By default when the DispatcherServlet can't find a handler for a request it sends a 404 response.
     However if its property "throwExceptionIfNoHandlerFound" is set to true this exception is raised and
      may be handled with a configured HandlerExceptionResolver.
     * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/NoHandlerFoundException.html
     * https://stackoverflow.com/questions/13356549/handle-error-404-with-spring-controller/46704230
     * */
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoHandlerFoundException.class)
    public ModelAndView resourceNotFoundException() {
        LOGGER.error("Error resourceNotFoundException caught");
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.Resource", null, locale);
        Long code = Long.valueOf(HttpStatus.NOT_FOUND.toString());
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = TypeMismatchException.class)
    public ModelAndView badRequestException() {
        LOGGER.error("Error badRequestException caught");
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.BadRequest", null, locale);
        Long code = Long.valueOf(HttpStatus.BAD_REQUEST.toString());
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }


    /*Server error */
//    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(value = Exception.class)
//    public ModelAndView serverException() {
//        LOGGER.error("Error serverException caught");
//        Locale locale = LocaleContextHolder.getLocale();
//        String error = messageSource.getMessage("errors.ServerError", null, locale);
//        Long code = Long.valueOf(HttpStatus.INTERNAL_SERVER_ERROR.toString());
//        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
//        mav.addObject("errors", error);
//        mav.addObject("code", code);
//        return mav;
//    }

}
