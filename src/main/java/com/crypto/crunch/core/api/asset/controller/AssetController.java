package com.crypto.crunch.core.api.asset.controller;

import com.crypto.crunch.core.api.common.model.DefaultResponse;
import com.crypto.crunch.core.api.asset.service.AssetService;
import com.crypto.crunch.core.domain.asset.conf.AssetConf;
import com.crypto.crunch.core.domain.asset.exception.AssetException;
import com.crypto.crunch.core.domain.asset.model.Asset;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequestMapping("/api/v1/asset")
@RestController
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @PostMapping(value = "/save")
    public ResponseEntity<DefaultResponse<?>> signup(@RequestBody Asset asset, @RequestHeader("Authorization") String accessToken) {
        try {
            return new ResponseEntity<>(DefaultResponse.<Asset>builder()
                    .data(assetService.save(asset, accessToken))
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.CREATED.value())
                    .build(), HttpStatus.CREATED);
        } catch (AssetException e) {
            log.error(String.format("error message : %s", e.getMessage()), e);

            AssetException.AssetExceptionType exceptionType = e.getType();
            if (exceptionType.equals(AssetException.AssetExceptionType.NOT_VALID_REQUEST)) {
                return new ResponseEntity<>(DefaultResponse.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build(), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(DefaultResponse.builder()
                        .message(e.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error(String.format("error message : %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/key")
    public ResponseEntity<DefaultResponse<?>> getAssetList(@RequestBody Map<String, String> body, @RequestHeader("Authorization") String accessToken) {
        try {
            AssetConf.AssetType AssetType = AssetConf.AssetType.valueOf(body.get("assetType"));
            DefaultResponse<List<Asset>> response = DefaultResponse.<List<Asset>>builder()
                    .data(assetService.findAssetByProvider(accessToken, AssetType))
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.OK.value())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AssetException e) {
            log.error(String.format("error message : %s", e.getMessage()));

            return new ResponseEntity<>(DefaultResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/key/{id}")
    public ResponseEntity<DefaultResponse<?>> getAsset(@PathVariable("id") Integer id) {
        try {
            Optional<Asset> Asset = assetService.findAssetById(id);
            if (Asset.isPresent()) {
                DefaultResponse<Asset> response = DefaultResponse.<Asset>builder()
                        .data(Asset.get())
                        .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                        .status(HttpStatus.OK.value())
                        .build();
                System.out.println(Asset);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                throw new AssetException(AssetException.AssetExceptionType.FAIL_TO_FIND_API_KEY, AssetException.FAIL_TO_FIND_API_KEY.getMessage());
            }
        } catch (AssetException e) {
            log.error(String.format("error message : %s", e.getMessage()));

            return new ResponseEntity<>(DefaultResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .build(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "")
    public ResponseEntity<DefaultResponse<?>> getAllAsset(@RequestHeader("Authorization") String accessToken) {
        try {
            DefaultResponse<List<Asset>> response = DefaultResponse.<List<Asset>>builder()
                    .data(assetService.findAllAssets(accessToken))
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(HttpStatus.OK.value())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (AssetException e) {
            log.error(String.format("error message : %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.builder()
                    .message(e.getMessage())
                    .status(HttpStatus.NOT_FOUND.value())
                    .build(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            log.error(String.format("error message: %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.FAIL_DEFAULT_RES, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
