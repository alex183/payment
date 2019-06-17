package com.wirecard.payment.http.converter;

import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.http.converter.impl.ReceiptResponseConverterImpl;
import com.wirecard.payment.http.data.response.PaymentReceiptResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.wirecard.payment.mock.Mocks.getPaymentReceiptCreditCardMock;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class PaymentReceiptResponseConverterTest {
    private ReceiptResponseConverter receiptResponseConverter;

    @Before
    public void setUp(){
        receiptResponseConverter = new ReceiptResponseConverterImpl();
    }

    @Test
    public void convertFromReceiptEntityToPaymentReceipt(){
        PaymentReceipt paymentReceiptCreditCardMock = getPaymentReceiptCreditCardMock();
        PaymentReceiptResponse paymentReceiptResponse = receiptResponseConverter.convertToResponse(paymentReceiptCreditCardMock);

        assertEquals(paymentReceiptCreditCardMock.getClient().getId(), paymentReceiptResponse.getClient().getId());
        assertEquals(paymentReceiptCreditCardMock.getBuyer().getCpf(), paymentReceiptResponse.getBuyer().getCpf());
        assertEquals(paymentReceiptCreditCardMock.getBuyer().getEmail(), paymentReceiptResponse.getBuyer().getEmail());
        assertEquals(paymentReceiptCreditCardMock.getBuyer().getName(), paymentReceiptResponse.getBuyer().getName());
        assertEquals(paymentReceiptCreditCardMock.getPayment().getAmount(), paymentReceiptResponse.getPayment().getAmount());
        assertEquals(paymentReceiptCreditCardMock.getPayment().getType(), paymentReceiptResponse.getPayment().getType());
    }
}