package com.wirecard.payment.usecase.impl;

import com.wirecard.payment.config.properties.ErrorMessagesProperties;
import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.exception.PaymentsNotFoundException;
import com.wirecard.payment.repositories.ReceiptRepository;
import com.wirecard.payment.repositories.converter.ReceiptEntityConverter;
import com.wirecard.payment.repositories.data.ReceiptEntity;
import com.wirecard.payment.usecase.GetPayments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class GetPaymentsImpl implements GetPayments {

    private final ReceiptRepository receiptRepository;
    private final ReceiptEntityConverter receiptEntityConverter;
    private final ErrorMessagesProperties errorMessagesProperties;

    @Override
    public List<PaymentReceipt> execute() {
        List<ReceiptEntity> receiptEntities = receiptRepository.findAll();

        List<PaymentReceipt> paymentReceipts = receiptEntityConverter.convertToUseCase(receiptEntities);

        if(isNull(paymentReceipts) | paymentReceipts.isEmpty()) throw new PaymentsNotFoundException(
                errorMessagesProperties.getPaymentsNotFound().getUserMessage(),
                errorMessagesProperties.getPaymentsNotFound().getDeveloperMessage()
        );

        return paymentReceipts;
    }
}
