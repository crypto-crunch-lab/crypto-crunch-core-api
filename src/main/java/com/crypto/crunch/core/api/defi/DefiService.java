package com.crypto.crunch.core.api.defi;

import com.crypto.crunch.core.domain.defi.Defi;

import java.util.List;

public interface DefiService {
    List<Defi> getList() throws Exception;
}
