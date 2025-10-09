package com.vicsergeev.SpringHW.exception;

import lombok.Getter;

/**
 * Created by Victor 09.10.2025
 */
@Getter
public class UserNotFoundException extends RuntimeException {
    private final Long userId;
    public UserNotFoundException(Long userId) {
        super("user with id " + userId + " not found");
        this.userId = userId;
    }
}
