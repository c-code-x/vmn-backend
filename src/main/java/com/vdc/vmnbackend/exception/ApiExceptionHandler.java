package com.vdc.vmnbackend.exception;

import com.vdc.vmnbackend.dto.res.ExceptionResDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.AuthenticationException;
import java.nio.file.AccessDeniedException;
import java.util.Date;

@RestControllerAdvice
@Slf4j
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthenticationException ex) {
        log.error("AuthenticationException: ", ex);
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(ApiRuntimeException.class)
    public ResponseEntity<ExceptionResDTO> exceptionHandler(ApiRuntimeException exception, WebRequest request) {
        ExceptionResDTO response = ExceptionResDTO.builder()
                .message(exception.getMessage())
                .timestamp(new Date())
                .httpStatus(exception.getHttpStatus())
                .build();
        return new ResponseEntity<>(response, response.httpStatus());
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionResDTO> exceptionHandler(AccessDeniedException exception, WebRequest request) {
        log.error("AccessDeniedException: ", exception);
        ExceptionResDTO response
                = ExceptionResDTO.builder()
                .message(exception.getMessage())
                .timestamp(new Date())
                .httpStatus(HttpStatus.FORBIDDEN)
                .build();
        return new ResponseEntity<>(response, response.httpStatus());
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResDTO> exceptionHandler(Exception exception, WebRequest request) {
        log.error("Exception: ", exception);
        ExceptionResDTO response = ExceptionResDTO.builder()
                .message(exception.getMessage())
                .timestamp(new Date())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
        return new ResponseEntity<>(response, response.httpStatus());
    }
}
