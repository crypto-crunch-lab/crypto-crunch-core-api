package com.crypto.crunch.core.domain.defi.model;

import lombok.*;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DefiNetwork {
    private String name;
    private String networkIconUrl;
}