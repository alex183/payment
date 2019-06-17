package com.wirecard.payment.http.converter;

import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.http.data.request.PaymentRequest;

public interface ReceiptConverter {
    PaymentReceipt convertToUseCase(PaymentRequest paymentRequest);
}
