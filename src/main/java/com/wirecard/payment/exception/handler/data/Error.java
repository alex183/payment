package com.wirecard.payment.exception.handler.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class Error {
    private String name;
    private String message;
}
