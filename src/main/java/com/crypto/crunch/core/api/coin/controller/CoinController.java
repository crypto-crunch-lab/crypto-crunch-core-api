package com.crypto.crunch.core.api.coin.controller;

import com.crypto.crunch.core.api.coin.service.CoinService;
import com.crypto.crunch.core.api.common.model.DefaultResponse;
import com.crypto.crunch.core.domain.coin.model.Coin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/api/v1/coin")
@RestController
public class CoinController {
    private final CoinService coinService;

    public CoinController(CoinService coinService){
        this.coinService = coinService;
    }

    @PostMapping(value="/save")
    public ResponseEntity<DefaultResponse<?>> save(@RequestHeader("accessToken") String accessToken, @RequestBody Coin coin){
        try{
            coinService.saveAveragePrice(accessToken, coin);
            return new ResponseEntity<>(DefaultResponse.<Coin>builder()
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.CREATED.value())
                    .build(), HttpStatus.CREATED);
        } catch (Exception e){
            log.error(String.format("error message : %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value="/")
    public ResponseEntity<DefaultResponse<?>> getList(@RequestHeader("accessToken") String accessToken){
        try{
            DefaultResponse<Optional<List<Coin>>> response = DefaultResponse.<Optional<List<Coin>>>builder()
                    .data(coinService.getCoinList(accessToken))
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.OK.value())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            log.error(String.format("error message : %s", e.getMessage()));
            return new ResponseEntity<>(DefaultResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .build(), HttpStatus.NOT_FOUND);
        }
    }
}
