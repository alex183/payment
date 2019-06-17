package com.wirecard.payment.http.data.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PaymentRequest {
    @Valid
    @NotNull
    private Client client;
    @Valid
    @NotNull
    private Buyer buyer;
    @Valid
    @NotNull
    private Payment payment;
}
