package com.crypto.crunch.core.api.defi;

import com.crypto.crunch.core.domain.defi.Defi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/defi")
public class DefiController {

    private final DefiService defiService;

    public DefiController(DefiService defiService) {
        this.defiService = defiService;
    }

    @GetMapping
    public List<Defi> getDefiList() throws Exception {
        return defiService.getList();
    }
}
