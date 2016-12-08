package com.opentable.resizer.transformers.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import static com.opentable.resizer.constants.Constant.ERROR;
import static com.opentable.resizer.constants.Constant.ERROR_MESSAGE;

/**
 * @author Vivek Wiki
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Throwable.class)
    public ModelAndView handleInputOutputException(final Exception ex) {
        return new ModelAndView(ERROR, ERROR_MESSAGE, ex.getLocalizedMessage());
    }


}
