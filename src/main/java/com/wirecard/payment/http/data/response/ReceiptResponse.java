package com.wirecard.payment.http.data.response;

import com.wirecard.payment.entity.enumerator.ReceiptStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptResponse {
    private String id;
    private ReceiptStatus status;
    private Client client;
    private Buyer buyer;
    private Payment payment;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;
}
