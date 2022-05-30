package com.crypto.crunch.core.api.protfolio.service;

import com.crypto.crunch.core.domain.portfolio.conf.PortfolioConf;
import com.crypto.crunch.core.domain.portfolio.model.Portfolio;

import java.util.List;
import java.util.Optional;

public interface PortfolioService {
    Portfolio save(Portfolio portfolio, String accessToken);

    List<Portfolio> findPortfolioByProvider(String accessToken, PortfolioConf.PortfolioType portfolioType);

    Optional<Portfolio> findPortfolioById(String accessToken, Integer id);

    List<Portfolio> findAllPortfolios(String accessToken);
}
