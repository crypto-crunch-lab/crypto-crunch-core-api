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
    private String id;          // 디파이 고유번호
    private String name;        // 디파이 이름
    private String platform;    // 플랫폼
    private String network;     // 네트워크
    private Double base;        // base
    private Double reward;      // reword
    private Double apy;         // apy
    private Long tvl;           // tvl
    private Integer risk;       // risk
    private String defiIconUrl;         // 디파이 아이콘 url
    private String platformIconUrl;     // 플랫폼 아이콘 url
    private String detailUrl;           // 코인딕스 상세 페이지
    private DefiConf.DefiCoinType coinType;
    private List<DefiConf.DefiAttributeType> attributes;
    private DefiSeries<Double> apySeries;    // 차트용 히스토리
    private DefiSeries<Long> tvlSeries;    // 차트용 히스토리
    private String syncYmdt;    // 싱크 일시(yyyy-MM-dd'T'HH:mm:ssZ)
    private String updateYmdt;  // 업데이트 일시(yyyy-MM-dd'T'HH:mm:ssZ)
    private String historyUpdateYmdt; // 차트용 히스토리 업데이트 일시 (yyyy-MM-dd'T'HH:mm:ssZ)
}