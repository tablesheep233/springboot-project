package org.table.neweims.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class MyExceptionHandler {

    private static Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    @ExceptionHandler
    public ModelAndView allExceptionHandler(Exception e){
        ModelAndView mv = new ModelAndView();

        return mv;
    }

    @ExceptionHandler
    public ModelAndView myExceptionHandler(MyException e){
        ModelAndView mv = new ModelAndView();

        return mv;
    }
}
