package com.crypto.crunch.core.api.user.service;

import com.crypto.crunch.core.domain.user.model.User;

public interface UserService {
    User save(User user);

    String authenticate(User user);

    User getUserByToken(String token);
}
