package com.crypto.crunch.core.api.defi.controller;

import com.crypto.crunch.core.api.defi.service.DefiService;
import com.crypto.crunch.core.domain.defi.Defi;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 디파이 어드민 API Controller
 */
@RequestMapping("/api/v1/defi/admin")
@RestController
public class DefiAdminController {

    private final DefiService defiService;

    public DefiAdminController(DefiService defiService) {
        this.defiService = defiService;
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateDefi(@RequestBody Defi defi) throws IOException {
        Boolean isSuccess = defiService.update(defi);
        return isSuccess ? ResponseEntity.ok().body(true) : ResponseEntity.badRequest().body(false);
    }
}
