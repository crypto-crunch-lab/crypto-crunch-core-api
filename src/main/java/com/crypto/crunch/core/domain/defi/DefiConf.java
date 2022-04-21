package com.crypto.crunch.core.domain.defi;

public class DefiConf {
    public enum DefiTvlRangeType {
        TVL_0(0), TVL_10K(1000), TVL_100K(10000), TVL_1M(1000000), TVL_10M(10000000), TVL_100M(100000000);

        private final Integer value;

        DefiTvlRangeType(Integer value) {
            this.value = value;
        }

        public Integer value() {
            return value;
        }
    }

    public enum DefiApyRangeType {
        APY_0(0), APY_10(10), APY_30(30), APY_50(50), APY_100(100), APY_200(200), APY_500(500), APY_1000(1000);

        private final Integer value;

        DefiApyRangeType(Integer value) {
            this.value = value;
        }

        public Integer value() {
            return value;
        }
    }
}
