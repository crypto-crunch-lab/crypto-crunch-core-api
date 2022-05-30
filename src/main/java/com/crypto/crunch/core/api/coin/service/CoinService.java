package com.crypto.crunch.core.api.coin.service;

import com.crypto.crunch.core.domain.coin.model.Coin;

import java.util.List;
import java.util.Optional;

public interface CoinService {
    void saveAveragePrice(String accesstoken, Coin coin);

    Optional<List<Coin>> getCoinList(String accesstoken);
}
