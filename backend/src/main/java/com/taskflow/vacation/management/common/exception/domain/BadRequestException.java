package com.taskflow.vacation.management.common.exception.domain;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final String messageKey;
    private final Object[] args;

    public BadRequestException(String messageKey, Object... args) {
        super(messageKey);
        this.messageKey = messageKey;
        this.args = args;
    }

    public BadRequestException(String messageKey, Throwable cause) {
        super(messageKey, cause);
        this.messageKey = messageKey;
        this.args = new Object[0];
    }
}
