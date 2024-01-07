package com.vdc.vmnbackend.dto.res;

import org.springframework.http.HttpStatus;

public record BasicResDTO(
        String message,
        HttpStatus status
){
}
