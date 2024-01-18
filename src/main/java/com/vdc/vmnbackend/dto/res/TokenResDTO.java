package com.vdc.vmnbackend.dto.res;

public record TokenResDTO(
        String accessToken,
        String refreshToken) {
}
