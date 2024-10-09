package com.elo7.space_probe.ui;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.elo7.space_probe.ui.probeMovement.ProbeMovementInputException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ProbeMovementInputException.class)
    @ResponseStatus(value = BAD_REQUEST)
    ErrorMessageDTO handleException(ProbeMovementInputException probeMovementInputException) {
        return new ErrorMessageDTO(probeMovementInputException.getMessage(), BAD_REQUEST);
    }
}
