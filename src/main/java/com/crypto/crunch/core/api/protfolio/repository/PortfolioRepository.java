package com.crypto.crunch.core.api.protfolio.repository;

import com.crypto.crunch.core.domain.portfolio.conf.PortfolioConf;
import com.crypto.crunch.core.domain.portfolio.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio, Integer> {
    List<Portfolio> findByPortfolioTypeAndUserId(PortfolioConf.PortfolioType portfolioType, Integer userId);
    List<Portfolio> findByUserId(Integer userId);
}
