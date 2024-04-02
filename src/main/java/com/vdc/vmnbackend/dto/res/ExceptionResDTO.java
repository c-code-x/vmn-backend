package com.vdc.vmnbackend.dto.res;

import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.util.Date;
@Builder
public record ExceptionResDTO(

        String message,
        Date timestamp,
        String path,
        HttpStatus httpStatus){
}
