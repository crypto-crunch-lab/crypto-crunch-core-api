package com.crypto.crunch.core.api.user.service;

import com.crypto.crunch.core.api.user.repository.UserRepository;
import com.crypto.crunch.core.common.jwt.JwtTokenProvider;
import com.crypto.crunch.core.common.jwt.UserAuthentication;
import com.crypto.crunch.core.domain.user.conf.UserConf;
import com.crypto.crunch.core.domain.user.exception.UserException;
import com.crypto.crunch.core.domain.user.model.LoginRequest;
import com.crypto.crunch.core.domain.user.model.LoginResponse;
import com.crypto.crunch.core.domain.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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
    public LoginResponse authenticate(LoginRequest request) {
        String email = request.getEmail();
        String authKey = request.getAuthKey();
        UserConf.UserLoginType loginType = request.getLoginType();

        LoginResponse response = new LoginResponse();
        Integer userId;
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isPresent()) {
            if (!passwordEncoder.matches(authKey, userOptional.get().getPassword())) {
                throw UserException.AUTHENTICATION_FAIL;
            }
            userId = userOptional.get().getId();
            response.setAuthType(UserConf.UserAuthType.LOGIN);
        } else {
            User user = User.builder()
                    .email(email)
                    .password(authKey)
                    .loginType(loginType)
                    .build();

            userId = this.save(user).getId();
            response.setAuthType(UserConf.UserAuthType.SIGNUP);
        }
        Authentication authentication = new UserAuthentication(userId.toString(), null, null);
        String token = JwtTokenProvider.generateToken(authentication);
        response.setToken(token);

        return response;
    }

    @Override
    public User save(User user) {
        // 회원 생성 요청 validation
        this.isValid(user);

        String password = user.getPassword();
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);

        try {
            return userRepository.save(user);
        } catch (DataAccessException e) {
            throw UserException.DB_TRANSACTION_FAIL;
        }
    }

    @Override
    public User getUserByToken(String token) {
        int userId = Integer.parseInt(JwtTokenProvider.getUserIdFromJWT(token));
        return userRepository.findById(userId).orElseThrow(() -> UserException.DB_TRANSACTION_FAIL);
    }

    private void isValid(User user) {
        String email = user.getEmail();
        String password = user.getPassword();
        if (StringUtils.isBlank(email) || StringUtils.isBlank(password)) {
            throw UserException.NOT_VALID_REQUEST;
        }
    }
}
