package com.crypto.crunch.core.api.image;

import com.crypto.crunch.core.common.aws.S3Uploader;
import com.crypto.crunch.core.domain.image.ImageConf;
import com.crypto.crunch.core.domain.image.ImageUploadRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 공통 이미지 처리 API Controller
 */
@RequestMapping("/api/v1/image")
@RestController
public class ImageController {
    private final S3Uploader s3Uploader;

    public ImageController(S3Uploader s3Uploader) {
        this.s3Uploader = s3Uploader;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestBody ImageUploadRequest request) throws IOException {
        ImageConf.ImageUploadType uploadType = request.getUploadType();
        if (uploadType.equals(ImageConf.ImageUploadType.FILE)) {
            String url = s3Uploader.upload(request.getMultipartFile(), null, request.getDirName(), null);
            return ResponseEntity.ok().body(url);
        } else if (uploadType.equals(ImageConf.ImageUploadType.URL)) {
            String url = s3Uploader.upload(null, request.getUrl(), request.getDirName(), request.getFileName());
            return ResponseEntity.ok().body(url);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
