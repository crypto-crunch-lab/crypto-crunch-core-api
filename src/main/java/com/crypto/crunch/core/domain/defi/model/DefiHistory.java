package com.crypto.crunch.core.domain.defi.model;

import com.crypto.crunch.core.domain.defi.conf.DefiConf;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DefiHistory<T> {
    private DefiConf.DefiHistoryType historyType;
    private T value;
    private String syncYmd;
}
