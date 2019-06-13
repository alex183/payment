package com.wirecard.payment.http.converter;

import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.http.data.response.ReceiptResponse;

import java.util.List;

public interface ReceiptResponseConverter {
    ReceiptResponse convertToResponse(PaymentReceipt paymentReceipt);

    List<ReceiptResponse> convertToResponse(List<PaymentReceipt> receipts);
}
