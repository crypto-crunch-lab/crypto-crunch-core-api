package com.crypto.crunch.core.api.common.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class DefaultResponse<T> {

    private int status;
    private String message;
    private T data;

    public static final String SUCCESS_DEFAULT_MESSAGE = "SUCCESS";
    public static final DefaultResponse<?> FAIL_DEFAULT_RES = new DefaultResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR");

    public DefaultResponse(final int status, final String message) {
        this.status = status;
        this.message = message;
    }

    public DefaultResponse(final int status, final String message, final T t) {
        this.status = status;
        this.message = message;
        this.data = t;
    }

    public static <T> DefaultResponse<T> createFail() {
        return new DefaultResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "INTERNAL_SERVER_ERROR");
    }
}