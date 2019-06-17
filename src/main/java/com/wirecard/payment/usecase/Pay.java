package com.wirecard.payment.usecase;

import com.wirecard.payment.entity.PaymentReceipt;

public interface Pay {
    PaymentReceipt execute(PaymentReceipt paymentReceipt);
}
