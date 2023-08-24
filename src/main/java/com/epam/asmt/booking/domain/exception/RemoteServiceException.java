package com.epam.asmt.booking.domain.exception;

public class RemoteServiceException extends RuntimeException {
    public RemoteServiceException(String msg) {
        super(msg);
    }
}
