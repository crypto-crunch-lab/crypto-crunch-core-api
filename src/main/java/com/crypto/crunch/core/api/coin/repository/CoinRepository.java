package com.crypto.crunch.core.api.coin.repository;

import com.crypto.crunch.core.domain.coin.model.Coin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CoinRepository extends JpaRepository<Coin,Long> {
    Optional<List<Coin>> findAllByUserId(Integer userId);
}
