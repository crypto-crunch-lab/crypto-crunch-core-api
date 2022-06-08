package com.crypto.crunch.core.domain.asset.exception;

import lombok.Getter;

@Getter
public class AssetException extends RuntimeException{

    public enum AssetExceptionType {
        NOT_VALID_REQUEST, DB_TRANSACTION_FAIL, FAIL_TO_FIND_API_KEY
    }

    public static final AssetException NOT_VALID_REQUEST = new AssetException(AssetExceptionType.NOT_VALID_REQUEST, "유효하지 않은 api 키 생성 요청값 입니다.");
    public static final AssetException DB_TRANSACTION_FAIL = new AssetException(AssetExceptionType.DB_TRANSACTION_FAIL, "api 키 생성 실패입니다.");
    public static final AssetException FAIL_TO_FIND_API_KEY = new AssetException(AssetExceptionType.FAIL_TO_FIND_API_KEY, "api 키가 존재하지 않습니다.");

    private AssetExceptionType type;

    public AssetException(AssetExceptionType type, String message) {
        super(message);
        this.type = type;
    }
}
