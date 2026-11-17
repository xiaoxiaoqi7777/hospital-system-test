package com.etc.hospital.exception;



public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }
}