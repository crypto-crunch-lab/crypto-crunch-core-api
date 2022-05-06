package com.crypto.crunch.core.user;

import com.crypto.crunch.core.api.user.service.UserServiceImpl;
import com.crypto.crunch.core.domain.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    UserServiceImpl userService;

    @Test
    void getUserByToken() {
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjUxODQ0MTg4LCJleHAiOjE2NTI0NDg5ODh9.g5j3l6pB3JaWg96ZYYcx78G2peuQvIv7CfArNUVr4S709eZdL_sPLrpCG1V_ReaN0wy-G1aexlARgDJ2-yDQiw";
        User user = userService.getUserByToken(token);
        log.info(user.toString());
    }
}
