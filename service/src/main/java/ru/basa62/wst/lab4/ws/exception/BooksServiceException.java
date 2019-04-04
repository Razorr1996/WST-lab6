package ru.basa62.wst.lab4.ws.exception;

import lombok.Getter;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "BooksServiceFault")
public class BooksServiceException extends Exception {
    @Getter
    private final BooksServiceFault faultInfo;

    public BooksServiceException(String message, BooksServiceFault faultInfo) {
        super(message);
        this.faultInfo = faultInfo;
    }

    public BooksServiceException(String message, Throwable cause, BooksServiceFault faultInfo) {
        super(message, cause);
        this.faultInfo = faultInfo;
    }
}
