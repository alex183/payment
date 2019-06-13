package com.wirecard.payment.usecase.impl;

import com.wirecard.payment.config.properties.ErrorMessagesProperties;
import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.entity.enumerator.PaymentType;
import com.wirecard.payment.entity.enumerator.ReceiptStatus;
import com.wirecard.payment.exception.MockedException;
import com.wirecard.payment.exception.PaymentTypeNotYetSupportedException;
import com.wirecard.payment.repositories.ReceiptRepository;
import com.wirecard.payment.repositories.converter.ReceiptEntityConverter;
import com.wirecard.payment.repositories.data.ReceiptEntity;
import com.wirecard.payment.usecase.Pay;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PayImpl implements Pay {

    private final ReceiptRepository receiptRepository;
    private final ReceiptEntityConverter receiptEntityConverter;
    private final ErrorMessagesProperties errorMessagesProperties;

    @Override
    public PaymentReceipt execute(PaymentReceipt paymentReceipt) {
        paymentReceipt.setStatus(ReceiptStatus.INITIATED);

        PaymentReceipt initiatedPaymentReceipt = initiatePayment(paymentReceipt);
        try {
            if (paymentReceipt.getPayment().getType().equals(PaymentType.CREDIT_CARD)) {
                processCreditCardPayment(initiatedPaymentReceipt);
            } else if (paymentReceipt.getPayment().getType().equals(PaymentType.BOLETO)) {
                processBoletoPayment(initiatedPaymentReceipt);
            } else {
                errorPayment(initiatedPaymentReceipt);
                throw new PaymentTypeNotYetSupportedException(
                        errorMessagesProperties.getPaymentTypeNotYetSupported().getUserMessage(),
                        errorMessagesProperties.getPaymentTypeNotYetSupported().getDeveloperMessage());
            }

            ReceiptEntity receipt = receiptRepository.findOne(initiatedPaymentReceipt.getId());

            return receiptEntityConverter.convertToUseCase(receipt);
        }catch (MockedException e){
            errorPayment(initiatedPaymentReceipt);
            throw e;
        }
    }

    private void processBoletoPayment(PaymentReceipt initiatedPaymentReceipt) {
        processPayment(initiatedPaymentReceipt);
        finishPayment(initiatedPaymentReceipt);
    }

    private void processCreditCardPayment(PaymentReceipt initiatedPaymentReceipt) {
        processPayment(initiatedPaymentReceipt);
        if("123".equals(initiatedPaymentReceipt.getClient().getId())){
            throw new MockedException(
                    errorMessagesProperties.getPaymentError().getUserMessage(),
                    errorMessagesProperties.getPaymentError().getDeveloperMessage()
            );
        }
        finishPayment(initiatedPaymentReceipt);
    }

    private void finishPayment(PaymentReceipt paymentReceipt) {
        paymentReceipt.setStatus(ReceiptStatus.COMPLETED);
        receiptRepository.save(receiptEntityConverter.convertToEntity(paymentReceipt));
    }

    private void errorPayment(PaymentReceipt paymentReceipt) {
        paymentReceipt.setStatus(ReceiptStatus.ERROR);
        receiptRepository.save(receiptEntityConverter.convertToEntity(paymentReceipt));
    }

    private void processPayment(PaymentReceipt paymentReceipt) {
        paymentReceipt.setStatus(ReceiptStatus.IN_PROCESS);
        receiptRepository.save(receiptEntityConverter.convertToEntity(paymentReceipt));
    }

    private PaymentReceipt initiatePayment(PaymentReceipt paymentReceipt) {
        ReceiptEntity receiptEntity = receiptEntityConverter.convertToEntity(paymentReceipt);
        ReceiptEntity entity = receiptRepository.save(receiptEntity);
        return receiptEntityConverter.convertToUseCase(entity);
    }
}
