package com.wirecard.payment.usecase;

import com.wirecard.payment.entity.PaymentReceipt;

import java.util.List;

public interface GetPayments {
    List<PaymentReceipt> execute();
}
