package com.crypto.crunch.core.api.portfolio.controller;

import com.crypto.crunch.core.api.portfolio.service.PortfolioService;
import com.crypto.crunch.core.api.common.model.DefaultResponse;
import com.crypto.crunch.core.domain.portfolio.model.Portfolio;
import com.crypto.crunch.core.domain.portfolio.model.PortfolioCreateRequest;
import com.crypto.crunch.core.domain.portfolio.model.PortfolioUpdateRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequestMapping("/api/v1/portfolio")
@RestController
public class PortfolioController {
    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<DefaultResponse<?>> save(@RequestHeader("Authorization") String accessToken, @RequestBody PortfolioCreateRequest portfolioCreateRequest) {
        try {
            portfolioService.saveAveragePrice(accessToken, portfolioCreateRequest);
            return new ResponseEntity<>(DefaultResponse.<Portfolio>builder()
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.CREATED.value())
                    .build(), HttpStatus.CREATED);
        } catch (Exception e) {
            log.error(String.format("error message : %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<DefaultResponse<?>> getList(@RequestHeader("Authorization") String accessToken) {
        try {
            DefaultResponse<List<Portfolio>> response = DefaultResponse.<List<Portfolio>>builder()
                    .data(portfolioService.getPortfolioList(accessToken).get())
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.OK.value())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("error message : %s", e.getMessage()));
            return new ResponseEntity<>(DefaultResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<DefaultResponse<?>> update(@PathVariable("id") Integer id, @RequestBody PortfolioUpdateRequest portfolioUpdateRequest) {
        try {
            System.out.println(id);
            portfolioService.updateAveragePrice(id, portfolioUpdateRequest);
            return new ResponseEntity<>(DefaultResponse.<Portfolio>builder()
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.OK.value())
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("error message : %s", e.getMessage()));
            return new ResponseEntity<>(DefaultResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .build(), HttpStatus.NOT_FOUND);
        }
    }
}
