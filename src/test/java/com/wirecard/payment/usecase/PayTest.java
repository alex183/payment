package com.wirecard.payment.usecase;

import com.wirecard.payment.config.properties.ErrorMessagesProperties;
import com.wirecard.payment.config.properties.data.ErrorMessages;
import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.entity.enumerator.PaymentType;
import com.wirecard.payment.exception.MockedException;
import com.wirecard.payment.exception.PaymentTypeNotYetSupportedException;
import com.wirecard.payment.repositories.ReceiptRepository;
import com.wirecard.payment.repositories.converter.ReceiptEntityConverter;
import com.wirecard.payment.repositories.converter.impl.ReceiptEntityConverterImpl;
import com.wirecard.payment.repositories.data.ReceiptEntity;
import com.wirecard.payment.usecase.impl.PayImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static com.wirecard.payment.mock.Mocks.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class PayTest {

    @Mock
    private ReceiptRepository receiptRepository;

    private ReceiptEntityConverter receiptEntityConverter;

    @Mock
    private ErrorMessagesProperties errorMessagesProperties;

    private Pay pay;

    @Before
    public void setUp(){
        receiptEntityConverter = new ReceiptEntityConverterImpl();
        pay = new PayImpl(receiptRepository, receiptEntityConverter,errorMessagesProperties);
        when(errorMessagesProperties.getPaymentError()).thenReturn(ErrorMessages.builder().build());
        when(errorMessagesProperties.getPaymentTypeNotYetSupported()).thenReturn(ErrorMessages.builder().build());
        when(receiptRepository.save(any(ReceiptEntity.class))).thenReturn(getReceiptEntityMock());
    }

    @Test
    public void executeBoletoPaymentOK(){
        pay.execute(getPaymentReceiptMock());
        verify(receiptRepository,times(3)).save(any(ReceiptEntity.class));
    }

    @Test
    public void executeCreditCardPaymentOK(){
        pay.execute(getPaymentReceiptCreditCardMock());
        verify(receiptRepository,times(3)).save(any(ReceiptEntity.class));
    }

    @Test(expected = MockedException.class)
    public void executeCreditCardPaymentWithError(){
        when(receiptRepository.save(any(ReceiptEntity.class))).thenReturn(getReceiptEntityCreditCardMock());
        PaymentReceipt paymentCreditCardMock = getPaymentReceiptCreditCardMock();
        paymentCreditCardMock.getClient().setId("123");
        pay.execute(paymentCreditCardMock);
        verify(receiptRepository,times(3)).save(any(ReceiptEntity.class));
    }

    @Test(expected = PaymentTypeNotYetSupportedException.class)
    public void executeCreditCardPaymentWithPaymentTypeNotSupported(){
        when(receiptRepository.save(any(ReceiptEntity.class))).thenReturn(getReceiptEntityCreditCardMock());
        PaymentReceipt paymentCreditCardMock = getPaymentReceiptCreditCardMock();
        paymentCreditCardMock.getPayment().setType(PaymentType.VOUCHER);
        pay.execute(paymentCreditCardMock);
        verify(receiptRepository,times(3)).save(any(ReceiptEntity.class));
    }

}