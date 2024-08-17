package ru.stepup.payservise.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.stepup.payservise.exceptions.IntegrationException;
import ru.stepup.limitservise.dto.ErrorResponseDto;



@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IntegrationException.class)
    public ErrorResponseDto handleItemException(IntegrationException exception) {
        log.info("#####################/api ControllerAdvice.IntegrationException: " + "\n xception.getHttpStatus().value() = " + exception.getHttpStatus().value() + "\n exception.getErrorMessage() = " + exception.getMessage());
        return new ErrorResponseDto(exception.getHttpStatus().value(), exception.getMessage());
    }

}
