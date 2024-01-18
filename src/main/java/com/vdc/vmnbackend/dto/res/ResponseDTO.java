package com.vdc.vmnbackend.dto.res;

public record ResponseDTO<D>(
        D data,
        BasicResDTO res
) {
}
