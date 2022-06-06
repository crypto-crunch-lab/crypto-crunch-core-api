package com.crypto.crunch.core.api.portfolio.service;

import com.crypto.crunch.core.api.portfolio.repository.PortfolioRepository;
import com.crypto.crunch.core.common.jwt.JwtTokenProvider;
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
    public void saveAveragePrice(String accesstoken, Portfolio portfolio) {
        try{
            int userId = Integer.parseInt(JwtTokenProvider.getUserIdFromJWT(accesstoken));
            portfolioRepository.save(portfolio);
        } catch (DataAccessException e) {
            // 예외처리
        }
    }

    @Override
    public Optional<List<Portfolio>> getPortfolioList(String accesstoken) {
        try{
            int userId = Integer.parseInt(JwtTokenProvider.getUserIdFromJWT(accesstoken));
            return portfolioRepository.findAllByAssetId(userId);
        } catch (Exception e){
            return Optional.empty();
        }
    }

    @Override
    public void updateAveragePrice(Long portfolioId, Long newAveragePrice) {
        try{
            Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
            portfolio.setAveragePrice(newAveragePrice);
            portfolioRepository.save(portfolio);
        } catch (Exception e){

        }
    }
}
