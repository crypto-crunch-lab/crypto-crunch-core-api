package com.crypto.crunch.core.api.protfolio.service;

import com.crypto.crunch.core.api.protfolio.repository.PortfolioRepository;
import com.crypto.crunch.core.domain.portfolio.conf.PortfolioConf;
import com.crypto.crunch.core.domain.portfolio.model.Portfolio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository) {
        this.portfolioRepository = portfolioRepository;
    }

    @Override
    public Portfolio save(Portfolio portfolio) {
        try {
            return portfolioRepository.save(portfolio);
        } catch (DataAccessException e) {
            // 예외처리
            return null;
        }
    }

    @Override
    public Optional<Portfolio> findPortfolioByProvider(Integer userId, String provider) {
        return portfolioRepository.findByUserIdAndPortfolioType(userId, PortfolioConf.PortfolioType.valueOf(provider));
    }

    @Override
    public List<Portfolio> findAllPortfolios(Integer userId) {
        return portfolioRepository.findByUserId(userId);
    }
}
