package ru.basa62.wst.lab6.rs.exceptions;

import lombok.Getter;

import javax.ws.rs.core.Response;

public class ServiceException extends Exception {
    @Getter
    private final String reason;

    private final Response.Status status;

    public ServiceException(String reason) {
        this(reason, Response.Status.BAD_REQUEST);
    }

    public ServiceException(String reason, Response.Status status) {
        this(null, reason, status);
    }

    public ServiceException(String message, String reason, Response.Status status) {
        super(reason);
        this.reason = reason;
        this.status = status;
    }
}
