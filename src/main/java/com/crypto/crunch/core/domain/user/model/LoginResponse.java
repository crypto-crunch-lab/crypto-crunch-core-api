package com.crypto.crunch.core.domain.user.model;

import com.crypto.crunch.core.domain.user.conf.UserConf;
import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private UserConf.UserAuthType authType;
}
