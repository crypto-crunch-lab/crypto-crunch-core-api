package com.crypto.crunch.core.api.defi;

import com.crypto.crunch.core.domain.defi.Defi;
import com.crypto.crunch.core.domain.defi.DefiRequest;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface DefiService {
    List<Defi> search(DefiRequest request) throws Exception;
    List<String> getNetworks() throws IOException;
    Map<String, Map<String, String>> getAdminMetaFields();
    Boolean update(Defi defi) throws IOException;
}
