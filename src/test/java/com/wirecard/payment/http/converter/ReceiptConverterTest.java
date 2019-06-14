package com.wirecard.payment.http.converter;

import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.http.converter.impl.ReceiptConverterImpl;
import com.wirecard.payment.http.data.request.PaymentRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.wirecard.payment.mock.Mocks.getPaymentRequestCreditCardMock;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ReceiptConverterTest {

    private ReceiptConverter receiptConverter;

    @Before
    public void setUp(){
        receiptConverter = new ReceiptConverterImpl();
    }

    @Test
    public void convertFromReceiptEntityToPaymentReceipt(){
        PaymentRequest paymentRequestCreditCardMock = getPaymentRequestCreditCardMock();
        PaymentReceipt paymentReceipt = receiptConverter.convertToUseCase(paymentRequestCreditCardMock);

        assertEquals(paymentRequestCreditCardMock.getClient().getId(),paymentReceipt.getClient().getId());
        assertEquals(paymentRequestCreditCardMock.getBuyer().getCpf(),paymentReceipt.getBuyer().getCpf());
        assertEquals(paymentRequestCreditCardMock.getBuyer().getEmail(),paymentReceipt.getBuyer().getEmail());
        assertEquals(paymentRequestCreditCardMock.getBuyer().getName(),paymentReceipt.getBuyer().getName());
        assertEquals(paymentRequestCreditCardMock.getPayment().getAmount(),paymentReceipt.getPayment().getAmount());
        assertEquals(paymentRequestCreditCardMock.getPayment().getType(),paymentReceipt.getPayment().getType());
    }

}