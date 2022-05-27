package com.crypto.crunch.core.api.coin.service;

import com.crypto.crunch.core.api.coin.repository.CoinRepository;
import com.crypto.crunch.core.common.jwt.JwtTokenProvider;
import com.crypto.crunch.core.domain.coin.model.Coin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CoinServiceImpl implements CoinService {
    private final CoinRepository coinRepository;

    public CoinServiceImpl(CoinRepository coinRepository) {
        this.coinRepository = coinRepository;
    }

    @Override
    public void saveAveragePrice(String accesstoken, Coin coin) {
        try{
            int userId = Integer.parseInt(JwtTokenProvider.getUserIdFromJWT(accesstoken));
            coin.setUserId(userId);
            coinRepository.save(coin);
        } catch (DataAccessException e) {
            // 예외처리
        }
    }

    @Override
    public Optional<List<Coin>> getCoinList(String accesstoken) {
        try{
            int userId = Integer.parseInt(JwtTokenProvider.getUserIdFromJWT(accesstoken));
            return coinRepository.findAllByUserId(userId);
        } catch (Exception e){
            return Optional.empty();
        }
    }
}
