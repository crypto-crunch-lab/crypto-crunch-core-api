package com.crypto.crunch.core.domain.user.model;

import com.crypto.crunch.core.domain.user.conf.UserConf;
import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String authKey;
    private UserConf.UserLoginType loginType;
}