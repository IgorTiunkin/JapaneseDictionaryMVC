package com.phantom.japanese_dictionary_mvc.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler (FileIOException.class)
    public String resultExportError(RuntimeException runtimeException) {
        LOGGER.info("Возникли проблемы при загрузке/выгрузке файла. {}", runtimeException.getMessage());
        return "error/IOFailed";
    }

}
