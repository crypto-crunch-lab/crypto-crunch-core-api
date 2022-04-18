package com.crypto.crunch.core.api.sample;

import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService {
    @Override
    public String hello() {
        return "hello";
    }
}
