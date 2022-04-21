package com.crypto.crunch.core.domain.defi;

import lombok.Data;

@Data
public class DefiRequest {
    private Integer size;
    private String searchKeyword;
    private DefiRequestSorts sorts;
    private DefiRequestFilters filters;
}
