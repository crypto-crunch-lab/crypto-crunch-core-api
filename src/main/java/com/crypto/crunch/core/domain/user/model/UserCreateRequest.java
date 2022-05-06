package com.crypto.crunch.core.domain.user.model;

import com.crypto.crunch.core.domain.user.conf.UserConf;
import lombok.Data;

@Data
public class UserCreateRequest {
    private String email;
    private String password;
    private UserConf.UserLoginType loginType;
}
