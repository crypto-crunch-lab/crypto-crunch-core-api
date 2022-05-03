package com.crypto.crunch.core.domain.defi.model;

import com.crypto.crunch.core.domain.defi.conf.DefiConf;
import lombok.Data;

@Data
public class DefiRequestFilters {
    private String network;
    private DefiConf.DefiTvlRangeType tvlRange;
    private DefiConf.DefiApyRangeType apyRange;
}
