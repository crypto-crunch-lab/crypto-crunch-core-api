package com.crypto.crunch.core.api.asset.repository;

import com.crypto.crunch.core.domain.asset.conf.AssetConf;
import com.crypto.crunch.core.domain.asset.model.Asset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssetRepository extends JpaRepository<Asset, Integer> {
    List<Asset> findByAssetTypeAndUserId(AssetConf.AssetType assetType, Integer userId);
    List<Asset> findByUserId(Integer userId);
}
