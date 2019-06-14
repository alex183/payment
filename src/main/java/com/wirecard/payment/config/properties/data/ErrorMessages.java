package com.wirecard.payment.config.properties.data;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessages {
    private String userMessage;
    private String developerMessage;
}
