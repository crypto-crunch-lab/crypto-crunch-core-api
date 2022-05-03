package com.crypto.crunch.core.api.defi.service;

import com.crypto.crunch.core.domain.defi.model.Defi;
import com.crypto.crunch.core.domain.defi.model.DefiHistory;
import com.crypto.crunch.core.domain.defi.model.DefiRequest;

import java.io.IOException;
import java.util.List;

public interface DefiService {

    /**
     * 디파이 검색
     *
     * @param request
     * @return
     * @throws Exception
     */
    List<Defi> search(DefiRequest request) throws Exception;

    /**
     * 전체 네트워크 조회
     *
     * @return
     * @throws IOException
     */
    List<String> getNetworks() throws IOException;

    /**
     * 디파이 히스토리(차트) 조회
     *
     * @param id
     * @return
     * @throws IOException
     */
    List<DefiHistory> getHistories(String id) throws IOException;

    /**
     * 디파이 업데이트
     *
     * @param defi
     * @return
     * @throws IOException
     */
    Boolean update(Defi defi) throws IOException;
}