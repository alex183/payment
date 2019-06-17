package com.wirecard.payment.http.data.request;

import com.wirecard.payment.entity.enumerator.PaymentType;
import com.wirecard.payment.http.validator.ConditionalValidation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@ConditionalValidation(selected = "type", values = {"CREDIT_CARD"}, required = {"card"})
@ConditionalValidation(selected = "type", values = {"BOLETO"}, required = {"boleto"})
public class Payment {
    @NotNull
    private BigDecimal amount;
    @NotNull
    private PaymentType type;
    @Valid
    private Card card;
    @Valid
    private Boleto boleto;
}
