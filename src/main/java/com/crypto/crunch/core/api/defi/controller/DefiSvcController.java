package com.crypto.crunch.core.api.defi.controller;

import com.crypto.crunch.core.api.defi.service.DefiService;
import com.crypto.crunch.core.domain.defi.Defi;
import com.crypto.crunch.core.domain.defi.DefiHistory;
import com.crypto.crunch.core.domain.defi.DefiRequest;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * 디파이 서비스 API Controller
 */
@RequestMapping("/api/v1/defi/svc")
@RestController
public class DefiSvcController {

    private final DefiService defiService;

    public DefiSvcController(DefiService defiService) {
        this.defiService = defiService;
    }

    @PostMapping
    public List<Defi> searchDefi(@RequestBody DefiRequest request) throws Exception {
        return defiService.search(request);
    }

    @GetMapping("/networks")
    public List<String> getNetworks() throws Exception {
        return defiService.getNetworks();
    }

    @GetMapping("/histories/{id}")
    public List<DefiHistory> getHistories(@PathVariable("id") String id) throws IOException {
        return defiService.getHistories(id);
    }
}
