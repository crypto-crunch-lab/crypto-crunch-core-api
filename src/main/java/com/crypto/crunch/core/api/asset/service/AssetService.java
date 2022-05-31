package com.crypto.crunch.core.api.asset.service;

import com.crypto.crunch.core.domain.asset.conf.AssetConf;
import com.crypto.crunch.core.domain.asset.model.Asset;

import java.util.List;
import java.util.Optional;

public interface AssetService {
    Asset save(Asset asset, String accessToken);

    List<Asset> findAssetByProvider(String accessToken, AssetConf.AssetType AssetType);

    Optional<Asset> findAssetById(String accessToken, Integer id);

    List<Asset> findAllAssets(String accessToken);
}
