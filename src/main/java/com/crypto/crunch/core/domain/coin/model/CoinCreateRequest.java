package com.crypto.crunch.core.domain.coin.model;

import lombok.Data;

@Data
public class CoinCreateRequest {
    private String symbol;
    private Long averagePrice;
    private Integer UserId;
}
