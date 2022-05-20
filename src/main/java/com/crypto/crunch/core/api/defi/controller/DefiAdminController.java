package com.crypto.crunch.core.api.defi.controller;

import com.crypto.crunch.core.api.common.model.DefaultResponse;
import com.crypto.crunch.core.api.defi.service.DefiService;
import com.crypto.crunch.core.domain.defi.model.Defi;
import com.crypto.crunch.core.domain.defi.model.DefiPlatform;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 디파이 어드민 API Controller
 */
@Slf4j
@RequestMapping("/api/v1/defi/admin")
@RestController
public class DefiAdminController {

    private final DefiService defiService;

    public DefiAdminController(DefiService defiService) {
        this.defiService = defiService;
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateDefi(@RequestBody Defi defi) throws IOException {
        Boolean isSuccess = defiService.updateDefi(defi);
        if (isSuccess) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/platforms")
    public ResponseEntity<DefaultResponse<List<DefiPlatform>>> getPlatforms() {
        try {
            return new ResponseEntity<>(DefaultResponse.<List<DefiPlatform>>builder()
                    .data(defiService.getPlatforms())
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.OK.value())
                    .build(), HttpStatus.OK);
        } catch (Exception e) {
            log.error(String.format("error message : %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.createFail(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/platforms/update")
    public ResponseEntity<Boolean> updatePlatform(@RequestBody DefiPlatform platform) throws IOException {
        Boolean isSuccess = defiService.updatePlatform(platform);
        if (isSuccess) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
