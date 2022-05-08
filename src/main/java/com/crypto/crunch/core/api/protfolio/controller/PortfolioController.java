package com.crypto.crunch.core.api.protfolio.controller;

import com.crypto.crunch.core.api.common.model.DefaultResponse;
import com.crypto.crunch.core.api.protfolio.service.PortfolioService;
import com.crypto.crunch.core.domain.portfolio.exception.PortfolioException;
import com.crypto.crunch.core.domain.portfolio.model.Portfolio;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/v1/portfolio")
@RestController
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    @PostMapping("/save")
    public ResponseEntity<DefaultResponse<?>> signup(@RequestBody Portfolio portfolio) {
        try {
            return new ResponseEntity<>(DefaultResponse.<Portfolio>builder()
                    .data(portfolioService.save(portfolio))
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.CREATED.value())
                    .build(), HttpStatus.CREATED);
        } catch (PortfolioException e) {
            log.error(String.format("error message : %s", e.getMessage()), e);

            PortfolioException.PortfolioExceptionType exceptionType = e.getType();
            if (exceptionType.equals(PortfolioException.PortfolioExceptionType.NOT_VALID_REQUEST)) {
                return new ResponseEntity<>(DefaultResponse.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build(), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(DefaultResponse.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error(String.format("error message : %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/Key")
    public ResponseEntity<DefaultResponse<?>> getPortfolio(@RequestBody Integer userId, String provider) {
        try {
            DefaultResponse<Portfolio> response = DefaultResponse.<Portfolio>builder()
                    .data(portfolioService.findPortfolioByProvider(userId, provider).get())
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.OK.value())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (PortfolioException e) {
            log.error(String.format("error message : %s", e.getMessage()));

            return new ResponseEntity<>(DefaultResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/")
    public ResponseEntity<DefaultResponse<?>> getAllPortfolio(@RequestBody Integer userId) {
        try {
            DefaultResponse<List<Portfolio>> response = DefaultResponse.<List<Portfolio>>builder()
                    .data(portfolioService.findAllPortfolios(userId))
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.OK.value())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (PortfolioException e) {
            log.error(String.format("error message : %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .build(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(String.format("error message: %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
