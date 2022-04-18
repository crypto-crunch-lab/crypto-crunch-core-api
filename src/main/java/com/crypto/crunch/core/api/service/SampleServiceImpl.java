package com.crypto.crunch.core.api.service;

import org.springframework.stereotype.Service;

@Service
public class SampleServiceImpl implements SampleService {
    @Override
    public String hello() {
        return "hello";
    }
}
