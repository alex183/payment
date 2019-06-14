package com.wirecard.payment.repositories.data;

import com.wirecard.payment.entity.enumerator.ReceiptStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Document(collection = "receipt")
public class ReceiptEntity {

    @Id
    private String id;
    private ReceiptStatus status;
    private Client client;
    private Buyer buyer;
    private Payment payment;
    private LocalDateTime creationDate;
    private LocalDateTime lastModifiedDate;


}
