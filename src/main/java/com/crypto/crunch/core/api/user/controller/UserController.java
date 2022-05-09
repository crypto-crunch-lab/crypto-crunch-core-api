package com.crypto.crunch.core.api.user.controller;

import com.crypto.crunch.core.api.common.model.DefaultResponse;
import com.crypto.crunch.core.api.user.service.UserService;
import com.crypto.crunch.core.domain.user.conf.UserConf;
import com.crypto.crunch.core.domain.user.exception.UserException;
import com.crypto.crunch.core.domain.user.model.LoginRequest;
import com.crypto.crunch.core.domain.user.model.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/v1/user")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<DefaultResponse<LoginResponse>> login(@RequestBody LoginRequest loginRequest) {
        try {
            LoginResponse response = userService.authenticate(loginRequest);
            HttpStatus status = response.getAuthType().equals(UserConf.UserAuthType.SIGNUP) ? HttpStatus.CREATED : HttpStatus.OK;
            return new ResponseEntity<>(DefaultResponse.<LoginResponse>builder()
                    .data(response)
                    .message(DefaultResponse.SUCCESS_DEFAULT_MESSAGE)
                    .status(status.value())
                    .build(), status);
        } catch (UserException e) {
            log.error(String.format("error message : %s", e.getMessage()), e);

            UserException.UserExceptionType exceptionType = e.getType();
            if (exceptionType.equals(UserException.UserExceptionType.NOT_VALID_REQUEST)) {
                return new ResponseEntity<>(DefaultResponse.<LoginResponse>builder()
                        .message(e.getMessage())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .build(), HttpStatus.BAD_REQUEST);
            } else {
                return new ResponseEntity<>(DefaultResponse.<LoginResponse>builder()
                        .message(e.getMessage())
                        .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            log.error(String.format("error message : %s", e.getMessage()), e);
            return new ResponseEntity<>(DefaultResponse.createFail(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
