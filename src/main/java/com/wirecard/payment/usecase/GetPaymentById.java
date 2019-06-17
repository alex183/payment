package com.wirecard.payment.usecase;

import com.wirecard.payment.entity.PaymentReceipt;

public interface GetPaymentById {
    PaymentReceipt execute(String id);
}
