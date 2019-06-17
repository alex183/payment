package com.wirecard.payment.usecase.impl;

import com.wirecard.payment.config.properties.ErrorMessagesProperties;
import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.exception.PaymentNotFoundException;
import com.wirecard.payment.repositories.ReceiptRepository;
import com.wirecard.payment.repositories.converter.ReceiptEntityConverter;
import com.wirecard.payment.repositories.data.ReceiptEntity;
import com.wirecard.payment.usecase.GetPaymentById;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
@RequiredArgsConstructor
public class GetPaymentByIdImpl implements GetPaymentById {

    private final ReceiptRepository receiptRepository;
    private final ReceiptEntityConverter receiptEntityConverter;
    private final ErrorMessagesProperties errorMessagesProperties;

    @Override
    public PaymentReceipt execute(String id) {
        ReceiptEntity receiptEntity = receiptRepository.findOne(id);
        PaymentReceipt paymentReceipt = receiptEntityConverter.convertToUseCase(receiptEntity);

        if(isNull(paymentReceipt)) throw new PaymentNotFoundException(
                errorMessagesProperties.getPaymentNotFound().getUserMessage(),
                errorMessagesProperties.getPaymentNotFound().getDeveloperMessage());

        return paymentReceipt;
    }
}
