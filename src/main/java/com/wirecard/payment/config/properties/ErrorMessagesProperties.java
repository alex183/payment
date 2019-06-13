package com.wirecard.payment.config.properties;

import com.wirecard.payment.config.properties.data.ErrorMessages;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@ConfigurationProperties("error.messages")
public class ErrorMessagesProperties {
    @NotNull
    private ErrorMessages paymentNotFound;
    @NotNull
    private ErrorMessages paymentsNotFound;
    @NotNull
    private ErrorMessages paymentTypeNotYetSupported;
    @NotNull
    private ErrorMessages paymentError;
}
