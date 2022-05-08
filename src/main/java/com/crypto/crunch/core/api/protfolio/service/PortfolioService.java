package com.crypto.crunch.core.api.protfolio.service;

import com.crypto.crunch.core.domain.portfolio.model.Portfolio;

import java.util.List;
import java.util.Optional;

public interface PortfolioService {
    Portfolio save(Portfolio portfolio);

    Optional<Portfolio> findPortfolioByProvider(Integer userId, String provider);

    List<Portfolio> findAllPortfolios(Integer userId);
}
