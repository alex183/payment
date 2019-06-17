package com.wirecard.payment.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ResourceException extends RuntimeException {
    private String userMessage;
    private String developerMessage;
}