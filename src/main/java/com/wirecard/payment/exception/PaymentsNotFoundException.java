package com.wirecard.payment.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PaymentsNotFoundException extends ResourceException {

    @Builder
    public PaymentsNotFoundException(String userMessage, String developerMessage) {
        super(userMessage, developerMessage);
    }
}