package com.crypto.crunch.core.common;

import com.crypto.crunch.core.common.aws.S3Uploader;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@Slf4j
@SpringBootTest
public class S3UploaderTest {
    @Autowired
    S3Uploader s3Uploader;

    @Test
    void upload() throws IOException {
        String url = s3Uploader.upload(null, "https://api.coindix.com/icons/UST.png", "image", "test.png");
        log.info(url);
    }
}
