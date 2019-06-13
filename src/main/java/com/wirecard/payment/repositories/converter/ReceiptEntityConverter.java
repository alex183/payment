package com.wirecard.payment.repositories.converter;

import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.repositories.data.ReceiptEntity;

import java.util.List;

public interface ReceiptEntityConverter {
    ReceiptEntity convertToEntity(PaymentReceipt paymentReceipt);
    PaymentReceipt convertToUseCase(ReceiptEntity receiptEntity);
    List<PaymentReceipt> convertToUseCase(List<ReceiptEntity> receiptEntities);
}
