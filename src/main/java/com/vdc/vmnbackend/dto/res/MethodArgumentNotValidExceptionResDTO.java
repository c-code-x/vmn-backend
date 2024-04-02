package com.vdc.vmnbackend.dto.res;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.Date;
import java.util.Map;

@Builder
public record MethodArgumentNotValidExceptionResDTO(
        String message,
        Date timestamp,
        String path,
        HttpStatus httpStatus,
        Map<String, Object> errors

) {
}
