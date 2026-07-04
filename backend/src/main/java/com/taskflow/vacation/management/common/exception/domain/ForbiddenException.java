package com.taskflow.vacation.management.common.exception.domain;

import lombok.Getter;

@Getter
public class ForbiddenException extends RuntimeException {

    private final String messageKey;
    private final Object[] args;

    public ForbiddenException(String messageKey, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }
}
