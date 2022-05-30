package com.crypto.crunch.core.domain.portfolio.model;


import com.crypto.crunch.core.domain.portfolio.conf.PortfolioConf;
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

    @Column(nullable = false)
    private Integer userId;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private PortfolioConf.PortfolioType portfolioType;

    @Column(nullable = false, length = 200)
    private String apiKey;

    @Column(nullable = false, length = 300)
    private String secretKey;

    public PortfolioConf.PortfolioType getPortfolioType() {
        return portfolioType;
    }
}
