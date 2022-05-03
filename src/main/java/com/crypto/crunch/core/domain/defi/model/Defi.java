package com.crypto.crunch.core.domain.defi.model;

import com.crypto.crunch.core.domain.defi.conf.DefiConf;
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
    private String defiIconUrl;
    private String platformIconUrl;
    private String detailUrl;
    private DefiConf.DefiCoinType coinType;
    private List<DefiConf.DefiAttributeType> attributes;
    private List<DefiHistory> histories;
}