package com.crypto.crunch.core.domain.defi.conf;

public class DefiConf {
    public static final String DEFI_INDEX = "defi";
    public static final Integer DEFI_INDEX_DEFAULT_SEARCH_SIZE = 5000;
    public static final String DEFI_INDEX_DEFAULT_SORT_FIELD = "tvl";

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
        APY_0(0.0), APY_10(0.1), APY_30(0.3), APY_50(0.5), APY_100(1.0), APY_200(2.0), APY_500(5.0), APY_1000(10.0);

        private final Double value;

        DefiApyRangeType(Double value) {
            this.value = value;
        }

        public Double value() {
            return value;
        }
    }

    public enum DefiCoinType {
        NONE("미설정"), LP_TOKEN("LP 토큰"), SINGLE_COIN("단일 코인"), STABLE_COIN("스테이블 코인"), NO_IMPERMANENT_LOSS("비영구적 손실 없음");

        private final String value;

        DefiCoinType(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public enum DefiAttributeType {
        SECURITY_AUDIT("보안감사"), VC_INVESTMENT("벤쳐캐피탈 투자유치"), REWARD_LOCK_UP("보상 Lock-up"), DEPOSIT_FEE("예치수수료"), DEPOSIT_LOCK_UP("에치금 Lock-up");

        private final String value;

        DefiAttributeType(String value) {
            this.value = value;
        }

        public String value() {
            return value;
        }
    }

    public enum DefiHistoryType {
        APY, TVL
    }
}
