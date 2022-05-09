package com.crypto.crunch.core.api.user.service;

import com.crypto.crunch.core.domain.user.model.LoginRequest;
import com.crypto.crunch.core.domain.user.model.LoginResponse;
import com.crypto.crunch.core.domain.user.model.User;

public interface UserService {
    LoginResponse authenticate(LoginRequest request);

    User save(User user);

    User getUserByToken(String token);
}
