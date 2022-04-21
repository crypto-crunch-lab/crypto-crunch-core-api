package com.crypto.crunch.core.domain.defi;

import lombok.Data;

@Data
public class DefiRequestFilters {
    private String network;
    private DefiConf.DefiTvlRangeType tvlRange;
    private DefiConf.DefiApyRangeType apyRange;
}
