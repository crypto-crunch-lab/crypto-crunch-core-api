package com.crypto.crunch.core.domain.defi;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class Defi {
    private String id;
    private String name;
    private String platform;
    private String network;
    private Double base;
    private Double reward;
    private Double apy;
    private Long tvl;
    private Integer risk;
    private String icon;
    private String link;
    private DefiConf.DefiCoinType coinType;
    private List<DefiConf.DefiAttributeType> attributes;
}
