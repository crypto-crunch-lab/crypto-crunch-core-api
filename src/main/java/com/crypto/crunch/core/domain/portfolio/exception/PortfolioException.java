package com.crypto.crunch.core.domain.portfolio.exception;

import lombok.Getter;

@Getter
public class PortfolioException extends RuntimeException{

    public enum PortfolioExceptionType {
        NOT_VALID_REQUEST, DB_TRANSACTION_FAIL, FAIL_TO_FIND_API_KEY
    }

    public static final PortfolioException NOT_VALID_REQUEST = new PortfolioException(PortfolioExceptionType.NOT_VALID_REQUEST, "유효하지 않은 api 키 생성 요청값 입니다.");
    public static final PortfolioException DB_TRANSACTION_FAIL = new PortfolioException(PortfolioExceptionType.DB_TRANSACTION_FAIL, "api 키 생성 실패입니다.");
    public static final PortfolioException FAIL_TO_FIND_API_KEY = new PortfolioException(PortfolioExceptionType.FAIL_TO_FIND_API_KEY, "api 키가 존재하지 않습니다.");

    private PortfolioExceptionType type;

    public PortfolioException(PortfolioExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
