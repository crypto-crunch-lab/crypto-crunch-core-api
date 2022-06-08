package com.crypto.crunch.core.api.portfolio.repository;

import com.crypto.crunch.core.domain.portfolio.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioRepository extends JpaRepository<Portfolio,Integer> {
    Optional<List<Portfolio>> findAllByUserId(Integer assetId);
}
