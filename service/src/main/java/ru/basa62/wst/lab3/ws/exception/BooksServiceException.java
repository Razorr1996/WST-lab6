package ru.basa62.wst.lab3.ws.exception;

import lombok.Getter;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "ru.basa62.wst.lab3.ws.exception.BooksServiceFault")
public class BooksServiceException extends Exception {
    @Getter
    private final BooksServiceFault fault;

    public BooksServiceException(String message, BooksServiceFault fault) {
        super(message);
        this.fault = fault;
    }

    public BooksServiceException(String message, Throwable cause, BooksServiceFault fault) {
        super(message, cause);
        this.fault = fault;
    }
}
