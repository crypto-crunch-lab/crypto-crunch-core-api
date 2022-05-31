package com.crypto.crunch.core.domain.portfolio.model;

import com.crypto.crunch.core.domain.asset.model.Asset;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "portfolio")
@ToString
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name="id")
    Asset asset;

    String symbol;

    Long averagePrice;
}
