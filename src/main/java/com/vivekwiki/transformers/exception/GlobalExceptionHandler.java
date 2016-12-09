package com.vivekwiki.transformers.exception;

import com.vivekwiki.transformers.constants.Constant;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Vivek Wiki
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public ModelAndView handleInputOutputException(final Exception ex) {
        return new ModelAndView(Constant.ERROR, Constant.ERROR_MESSAGE, ex.getLocalizedMessage());
    }


}
