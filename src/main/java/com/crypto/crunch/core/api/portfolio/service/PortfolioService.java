package com.crypto.crunch.core.api.portfolio.service;

import com.crypto.crunch.core.domain.portfolio.model.Portfolio;

import java.util.List;
import java.util.Optional;

public interface PortfolioService {
    void saveAveragePrice(String accesstoken, Portfolio portfolio);

    Optional<List<Portfolio>> getPortfolioList(String accesstoken);

    void updateAveragePrice(Long portfolioId, Long newAveragePrice);
}
