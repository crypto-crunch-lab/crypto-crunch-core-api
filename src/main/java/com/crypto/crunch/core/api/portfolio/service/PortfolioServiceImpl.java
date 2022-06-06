package com.crypto.crunch.core.api.portfolio.service;

import com.crypto.crunch.core.api.asset.repository.AssetRepository;
import com.crypto.crunch.core.api.portfolio.repository.PortfolioRepository;
import com.crypto.crunch.core.common.jwt.JwtTokenProvider;
import com.crypto.crunch.core.domain.asset.model.Asset;
import com.crypto.crunch.core.domain.portfolio.model.Portfolio;
import com.crypto.crunch.core.domain.portfolio.model.PortfolioCreateRequest;
import com.crypto.crunch.core.domain.portfolio.model.PortfolioUpdateRequest;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PortfolioServiceImpl implements PortfolioService {
    private final PortfolioRepository portfolioRepository;
    private final AssetRepository assetRepository;

    public PortfolioServiceImpl(PortfolioRepository portfolioRepository, AssetRepository assetRepository) {
        this.portfolioRepository = portfolioRepository;
        this.assetRepository = assetRepository;
    }

    @Override
    public void saveAveragePrice(String accesstoken, PortfolioCreateRequest portfolioCreateRequest) {
        try {
            Asset asset = assetRepository.findById(portfolioCreateRequest.getAssetId()).get();
            Long averagePrice = portfolioCreateRequest.getAveragePrice();
            String symbol = portfolioCreateRequest.getSymbol();
            Long userId = Long.valueOf(JwtTokenProvider.getUserIdFromJWT(accesstoken));
            Portfolio portfolio = Portfolio.builder()
                    .averagePrice(averagePrice)
                    .asset(asset)
                    .symbol(symbol)
                    .userId(userId)
                    .build();
            portfolioRepository.save(portfolio);
        } catch (DataAccessException e) {
            // 예외처리
        }
    }

    @Override
    public Optional<List<Portfolio>> getPortfolioList(String accesstoken) {
        try {
            Integer userId = Integer.parseInt(JwtTokenProvider.getUserIdFromJWT(accesstoken));
            return portfolioRepository.findAllByUserId(userId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public void updateAveragePrice(Integer portfolioId, PortfolioUpdateRequest portfolioUpdateRequest) {
        try {
            Long newAveragePrice = portfolioUpdateRequest.getAveragePrice();
            System.out.println(newAveragePrice);
            Portfolio portfolio = portfolioRepository.findById(portfolioId).get();
            System.out.println(portfolio);
            portfolio.setAveragePrice(newAveragePrice);
            portfolio.setSymbol("test");
            System.out.println(portfolio);
            portfolioRepository.save(portfolio);
        } catch (Exception e) {

        }
    }
}
