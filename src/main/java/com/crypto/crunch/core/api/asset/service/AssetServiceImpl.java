package com.crypto.crunch.core.api.asset.service;

import com.crypto.crunch.core.api.asset.repository.AssetRepository;
import com.crypto.crunch.core.common.jwt.JwtTokenProvider;
import com.crypto.crunch.core.domain.asset.conf.AssetConf;
import com.crypto.crunch.core.domain.asset.model.Asset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;

    public AssetServiceImpl(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @Override
    public Asset save(Asset Asset, String accessToken) {
        try {
            int userId = Integer.parseInt(JwtTokenProvider.getUserIdFromJWT(accessToken));
            System.out.println(userId);
            Asset.setUserId(userId);
            return assetRepository.save(Asset);
        } catch (DataAccessException e) {
            // 예외처리
            return null;
        }
    }

    @Override
    public List<Asset> findAssetByProvider(String accessToken, AssetConf.AssetType assetType) {
        Integer userId = Integer.valueOf(JwtTokenProvider.getUserIdFromJWT(accessToken));
        return assetRepository.findByAssetTypeAndUserId(assetType, userId);
    }

    @Override
    public Optional<Asset> findAssetById(String accessToken, Integer id) {
//        Integer userId = Integer.valueOf(JwtTokenProvider.getUserIdFromJWT(accessToken));
        return assetRepository.findById(id);
    }

    @Override
    public List<Asset> findAllAssets(String accessToken) {
        System.out.println(accessToken);
        Integer userId = Integer.valueOf(JwtTokenProvider.getUserIdFromJWT(accessToken));
        return assetRepository.findByUserId(userId);
    }
}
