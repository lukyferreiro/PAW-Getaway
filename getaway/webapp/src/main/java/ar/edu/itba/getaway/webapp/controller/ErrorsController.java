package ar.edu.itba.getaway.webapp.controller;

import ar.edu.itba.getaway.webapp.exceptions.ExperienceNotFoundException;
import org.springframework.beans.TypeMismatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Locale;

@ControllerAdvice
public class ErrorsController {

    public static final String NOT_FOUND_VIEW = "4XXerror";  //Page Not Found
    public static final String ERROR_VIEW = "5XXerror";   //Server error

    @Autowired
    private MessageSource messageSource;


    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = ExperienceNotFoundException.class)
    public ModelAndView userNotFound(){
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.Experience", null, locale);
        String code = HttpStatus.NOT_FOUND.toString();
        final ModelAndView mav = new ModelAndView(NOT_FOUND_VIEW);
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
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.NotFound.Resource", null, locale);
        String code = HttpStatus.NOT_FOUND.toString();
        final ModelAndView mav = new ModelAndView(NOT_FOUND_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = TypeMismatchException.class)
    public ModelAndView badRequestException() {
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.BadRequest", null, locale);
        String code = HttpStatus.BAD_REQUEST.toString();
        final ModelAndView mav = new ModelAndView(NOT_FOUND_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

    /*Server error */
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    public ModelAndView serverException() {
        Locale locale = LocaleContextHolder.getLocale();
        String error = messageSource.getMessage("errors.ServerError", null, locale);
        String code = HttpStatus.INTERNAL_SERVER_ERROR.toString();
        final ModelAndView mav = new ModelAndView(ERROR_VIEW);
        mav.addObject("errors", error);
        mav.addObject("code", code);
        return mav;
    }

}