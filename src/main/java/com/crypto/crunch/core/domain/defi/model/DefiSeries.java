package com.crypto.crunch.core.domain.defi.model;

import lombok.Data;

import java.util.List;

@Data
public class DefiSeries<T> {
    private List<DefiHistory<T>> histories;
    private T maxValue;
    private T minValue;
    private String startYmd;
    private String endYmd;
    private Integer count;
}
