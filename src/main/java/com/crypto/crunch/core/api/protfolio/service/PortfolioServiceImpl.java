package com.crypto.crunch.core.api.protfolio.service;

import com.crypto.crunch.core.api.protfolio.repository.PortfolioRepository;
import com.crypto.crunch.core.common.jwt.JwtTokenProvider;
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
    public Portfolio save(Portfolio portfolio, String accessToken) {
        try {
            int userId = Integer.parseInt(JwtTokenProvider.getUserIdFromJWT(accessToken));
            System.out.println(userId);
            portfolio.setUserId(userId);
            return portfolioRepository.save(portfolio);
        } catch (DataAccessException e) {
            // 예외처리
            return null;
        }
    }

    @Override
    public List<Portfolio> findPortfolioByProvider(String accessToken, PortfolioConf.PortfolioType portfolioType) {
        Integer userId = Integer.valueOf(JwtTokenProvider.getUserIdFromJWT(accessToken));
        return portfolioRepository.findByPortfolioTypeAndUserId(portfolioType, userId);
    }

    @Override
    public Optional<Portfolio> findPortfolioById(String accessToken, Integer id) {
//        Integer userId = Integer.valueOf(JwtTokenProvider.getUserIdFromJWT(accessToken));
        return portfolioRepository.findById(id);
    }

    @Override
    public List<Portfolio> findAllPortfolios(String accessToken) {
        System.out.println(accessToken);
        Integer userId = Integer.valueOf(JwtTokenProvider.getUserIdFromJWT(accessToken));
        return portfolioRepository.findByUserId(userId);
    }
}
