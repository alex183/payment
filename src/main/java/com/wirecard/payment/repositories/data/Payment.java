package com.wirecard.payment.repositories.data;

import com.wirecard.payment.entity.enumerator.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Payment {
    private BigDecimal amount;
    private PaymentType type;
    private Card card;
    private Boleto boleto;
}
