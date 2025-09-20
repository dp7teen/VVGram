package com.dp.vvgram.exceptions;

public class ExpiredJWTException extends Exception {
    public ExpiredJWTException(String message) {
        super(message);
    }
}
