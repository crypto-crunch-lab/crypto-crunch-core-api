package com.crypto.crunch.core.domain.defi.model;

import com.crypto.crunch.core.domain.defi.conf.DefiConf;
import lombok.Data;

@Data
public class DefiRequest {
    private Integer size;
    private String searchKeyword;
    private DefiRequestSorts sorts;
    private DefiRequestFilters filters;
    private DefiConf.DefiExposureType exposureType = DefiConf.DefiExposureType.SVC;
}
