package com.crypto.crunch.core.domain.asset.model;


import com.crypto.crunch.core.domain.asset.conf.AssetConf;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "asset")
@ToString
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private AssetConf.AssetType assetType;

    @Column(nullable = false, length = 200)
    private String apiKey;

    @Column(nullable = false, length = 300)
    private String secretKey;

    public AssetConf.AssetType getAssetType() {
        return assetType;
    }
}
