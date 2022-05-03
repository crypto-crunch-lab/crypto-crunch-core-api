package com.crypto.crunch.core.api.defi.controller;

import com.crypto.crunch.core.api.common.model.DefaultResponse;
import com.crypto.crunch.core.api.defi.service.DefiService;
import com.crypto.crunch.core.domain.defi.model.DefiRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

/**
 * 디파이 서비스 API Controller
 */
@Slf4j
@RequestMapping("/api/v1/defi/svc")
@RestController
public class DefiSvcController {

    private final DefiService defiService;

    public DefiSvcController(DefiService defiService) {
        this.defiService = defiService;
    }

    @PostMapping
    public ResponseEntity<DefaultResponse> searchDefi(@RequestBody DefiRequest request) {

        try {
            return new ResponseEntity<>(DefaultResponse.builder()
                    .data(defiService.search(request))
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.OK.value())
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("error message : %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/networks")
    public ResponseEntity<DefaultResponse> getNetworks() {
        try {
            return new ResponseEntity<>(DefaultResponse.builder()
                    .data(defiService.getNetworks())
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.OK.value())
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("error message : %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/histories/{id}")
    public ResponseEntity<DefaultResponse> getHistories(@PathVariable("id") String id) throws IOException {
        try {
            return new ResponseEntity<>(DefaultResponse.builder()
                    .data(defiService.getHistories(id))
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.OK.value())
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("error message : %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
