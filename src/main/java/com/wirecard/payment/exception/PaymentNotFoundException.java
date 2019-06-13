package com.wirecard.payment.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PaymentNotFoundException extends ResourceException {

    @Builder
    public PaymentNotFoundException(String userMessage, String developerMessage) {
        super(userMessage, developerMessage);
    }
}
