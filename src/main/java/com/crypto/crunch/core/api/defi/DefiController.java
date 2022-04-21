package com.crypto.crunch.core.api.defi;

import com.crypto.crunch.core.domain.defi.Defi;
import com.crypto.crunch.core.domain.defi.DefiRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/v1/defi")
@RestController
public class DefiController {

    private final DefiService defiService;

    public DefiController(DefiService defiService) {
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
}
