package com.wirecard.payment.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class MockedException extends ResourceException {

    @Builder
    public MockedException(String userMessage, String developerMessage) {
        super(userMessage, developerMessage);
    }
}
