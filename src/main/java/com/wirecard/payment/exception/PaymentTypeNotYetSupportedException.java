package com.wirecard.payment.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PaymentTypeNotYetSupportedException extends ResourceException {

    @Builder
    public PaymentTypeNotYetSupportedException(String userMessage, String developerMessage) {
        super(userMessage, developerMessage);
    }
}
