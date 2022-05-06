package com.crypto.crunch.core.domain.user.exception;

import lombok.Getter;

@Getter
public class UserException extends RuntimeException {

    public enum UserExceptionType {
        NOT_VALID_REQUEST, DB_TRANSACTION_FAIL, AUTHENTICATION_FAIL
    }

    public static final UserException NOT_VALID_REQUEST = new UserException(UserExceptionType.NOT_VALID_REQUEST, "유효하지 않은 회원 생성 요청값 입니다.");
    public static final UserException DB_TRANSACTION_FAIL = new UserException(UserExceptionType.DB_TRANSACTION_FAIL, "회원 생성 실패입니다.");
    public static final UserException AUTHENTICATION_FAIL = new UserException(UserExceptionType.AUTHENTICATION_FAIL, "회원 인증 실패입니다.");

    private UserExceptionType type;

    public UserException(UserExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
