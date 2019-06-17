package com.wirecard.payment.usecase;

import com.wirecard.payment.config.properties.ErrorMessagesProperties;
import com.wirecard.payment.config.properties.data.ErrorMessages;
import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.exception.PaymentNotFoundException;
import com.wirecard.payment.repositories.ReceiptRepository;
import com.wirecard.payment.repositories.converter.ReceiptEntityConverter;
import com.wirecard.payment.repositories.converter.impl.ReceiptEntityConverterImpl;
import com.wirecard.payment.usecase.impl.GetPaymentByIdImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import static com.wirecard.payment.mock.Mocks.getReceiptEntityCreditCardMock;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GetPaymentByIdTest {

    private GetPaymentById getPaymentById;

    @Mock
    private ReceiptRepository receiptRepository;

    private ReceiptEntityConverter receiptEntityConverter;

    @Mock
    private ErrorMessagesProperties errorMessagesProperties;

    @Before
    public void setUp(){
        receiptEntityConverter = new ReceiptEntityConverterImpl();
        getPaymentById = new GetPaymentByIdImpl(receiptRepository, receiptEntityConverter,errorMessagesProperties);
        when(errorMessagesProperties.getPaymentNotFound()).thenReturn(ErrorMessages.builder().build());
        when(receiptRepository.findOne(anyString())).thenReturn(getReceiptEntityCreditCardMock());
    }

    @Test
    public void mustReturnPaymentReceipts(){
        PaymentReceipt paymentReceipt = getPaymentById.execute("321");
        assertNotNull(paymentReceipt);
    }

    @Test(expected = PaymentNotFoundException.class)
    public void mustReturnPaymentsNotFoundExceptionWhenNullReturn(){
        when(receiptRepository.findOne("321")).thenReturn(null);
        getPaymentById.execute("321");
    }

}