package com.crypto.crunch.core.api.portfolio.service;

import com.crypto.crunch.core.domain.portfolio.model.Portfolio;
import com.crypto.crunch.core.domain.portfolio.model.PortfolioCreateRequest;
import com.crypto.crunch.core.domain.portfolio.model.PortfolioUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface PortfolioService {
    void saveAveragePrice(String accesstoken, PortfolioCreateRequest portfolio);

    Optional<List<Portfolio>> getPortfolioList(String accesstoken);

    void updateAveragePrice(Integer portfolioId, PortfolioUpdateRequest portfolioUpdateRequest);
}
