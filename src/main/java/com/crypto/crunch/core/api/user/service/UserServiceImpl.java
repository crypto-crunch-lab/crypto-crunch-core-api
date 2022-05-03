package com.crypto.crunch.core.api.user.service;

import com.crypto.crunch.core.api.user.repository.UserRepository;
import com.crypto.crunch.core.domain.user.model.User;
import com.crypto.crunch.core.domain.user.exception.UserException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User save(User user) {

        // 회원 생성 요청 validation
        this.isValid(user);

        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        try {
            User savedUser = userRepository.save(user);
            savedUser.setPassword(null);
            return savedUser;
        } catch (DataAccessException e) {
            throw UserException.DB_TRANSACTION_FAIL;
        }
    }

    @Override
    public User authenticate(User user) {
        String email = user.getEmail();
        String password = user.getPassword();

        User savedUser = userRepository.findByEmail(email).orElseThrow(() -> UserException.AUTHENTICATION_FAIL);
        if (!passwordEncoder.matches(password, savedUser.getPassword())) {
            throw UserException.AUTHENTICATION_FAIL;
        }
        savedUser.setPassword(null);
        return savedUser;
    }

    private void isValid(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            throw UserException.NOT_VALID_REQUEST;
        }
    }
}
