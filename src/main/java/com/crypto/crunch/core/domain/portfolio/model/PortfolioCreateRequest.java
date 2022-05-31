package com.crypto.crunch.core.domain.portfolio.model;

import lombok.Data;

@Data
public class PortfolioCreateRequest {
    private String symbol;
    private Long averagePrice;
    private Integer assetId;
}
