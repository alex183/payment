package com.wirecard.payment.entity;

import com.wirecard.payment.entity.enumerator.ReceiptStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class PaymentReceipt {
    private String id;
    private ReceiptStatus status;
    private Client client;
    private Buyer buyer;
    private Payment payment;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
}
