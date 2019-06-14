package com.wirecard.payment.http.converter;

import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.http.data.response.PaymentReceiptResponse;

import java.util.List;

public interface ReceiptResponseConverter {
    PaymentReceiptResponse convertToResponse(PaymentReceipt paymentReceipt);

    List<PaymentReceiptResponse> convertToResponse(List<PaymentReceipt> receipts);
}
