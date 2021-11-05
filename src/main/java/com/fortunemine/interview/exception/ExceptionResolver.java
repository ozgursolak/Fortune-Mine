package com.fortunemine.interview.exception;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionResolver extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException(final BaseException exception) {
        final ErrorDetails errorDetails = new ErrorDetails();
        final HttpHeaders headers = new HttpHeaders();

        errorDetails.setMessage(exception.getMessage());

        headers.setContentType(MediaType.APPLICATION_JSON);

        return new ResponseEntity<>(errorDetails, headers, exception.getHttpStatus());
    }
}
