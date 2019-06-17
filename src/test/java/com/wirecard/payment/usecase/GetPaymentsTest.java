package com.wirecard.payment.usecase;

import com.wirecard.payment.config.properties.ErrorMessagesProperties;
import com.wirecard.payment.config.properties.data.ErrorMessages;
import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.exception.PaymentsNotFoundException;
import com.wirecard.payment.mock.Mocks;
import com.wirecard.payment.repositories.ReceiptRepository;
import com.wirecard.payment.repositories.converter.ReceiptEntityConverter;
import com.wirecard.payment.repositories.converter.impl.ReceiptEntityConverterImpl;
import com.wirecard.payment.usecase.impl.GetPaymentsImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class GetPaymentsTest {

    private GetPayments getPayments;

    @Mock
    private ReceiptRepository receiptRepository;

    private ReceiptEntityConverter receiptEntityConverter;

    @Mock
    private ErrorMessagesProperties errorMessagesProperties;

    @Before
    public void setUp(){
        receiptEntityConverter = new ReceiptEntityConverterImpl();
        getPayments = new GetPaymentsImpl(receiptRepository, receiptEntityConverter,errorMessagesProperties);
        when(errorMessagesProperties.getPaymentsNotFound()).thenReturn(ErrorMessages.builder().build());
        when(receiptRepository.findAll()).thenReturn(asList(Mocks.getReceiptEntityCreditCardMock()));
    }

    @Test
    public void mustReturnPaymentReceipts(){
        List<PaymentReceipt> receipts = getPayments.execute();
        assertNotNull(receipts);
    }

    @Test(expected = PaymentsNotFoundException.class)
    public void mustReturnPaymentsNotFoundExceptionWhenNullReturn(){
        when(receiptRepository.findAll()).thenReturn(null);
        getPayments.execute();
    }
}