package com.wirecard.payment.repositories.converter;

import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.repositories.converter.impl.ReceiptEntityConverterImpl;
import com.wirecard.payment.repositories.data.ReceiptEntity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static com.wirecard.payment.mock.Mocks.getPaymentReceiptMock;
import static com.wirecard.payment.mock.Mocks.getReceiptEntityMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
public class ReceiptEntityConverterTest {

    private ReceiptEntityConverter receiptEntityConverter;

    @Before
    public void setUp(){
        receiptEntityConverter = new ReceiptEntityConverterImpl();
    }

    @Test
    public void convertFromReceiptEntityToPaymentReceipt(){
        ReceiptEntity receiptEntity = getReceiptEntityMock();
        PaymentReceipt paymentReceipt = receiptEntityConverter.convertToUseCase(receiptEntity);

        assertEquals(receiptEntity.getId(),paymentReceipt.getId());
        assertEquals(receiptEntity.getStatus(),paymentReceipt.getStatus());
        assertEquals(receiptEntity.getCreationDate(),paymentReceipt.getCreationDate());
        assertEquals(receiptEntity.getLastModifiedDate(),paymentReceipt.getLastModifiedDate());
        assertEquals(receiptEntity.getClient().getId(),paymentReceipt.getClient().getId());
        assertEquals(receiptEntity.getBuyer().getCpf(),paymentReceipt.getBuyer().getCpf());
        assertEquals(receiptEntity.getBuyer().getEmail(),paymentReceipt.getBuyer().getEmail());
        assertEquals(receiptEntity.getBuyer().getName(),paymentReceipt.getBuyer().getName());
        assertEquals(receiptEntity.getPayment().getAmount(),paymentReceipt.getPayment().getAmount());
        assertEquals(receiptEntity.getPayment().getType(),paymentReceipt.getPayment().getType());
    }

    @Test
    public void convertFromPaymentReceiptToReceiptEntity(){
        PaymentReceipt paymentReceipt = getPaymentReceiptMock();
        ReceiptEntity receiptEntity = receiptEntityConverter.convertToEntity(paymentReceipt);

        assertEquals(paymentReceipt.getId(),receiptEntity.getId());
        assertEquals(paymentReceipt.getStatus(),receiptEntity.getStatus());
        assertEquals(paymentReceipt.getCreationDate(),receiptEntity.getCreationDate());
        assertNotNull(paymentReceipt.getLastModifiedDate());
        assertEquals(paymentReceipt.getClient().getId(),receiptEntity.getClient().getId());
        assertEquals(paymentReceipt.getBuyer().getCpf(),receiptEntity.getBuyer().getCpf());
        assertEquals(paymentReceipt.getBuyer().getEmail(),receiptEntity.getBuyer().getEmail());
        assertEquals(paymentReceipt.getBuyer().getName(),receiptEntity.getBuyer().getName());
        assertEquals(paymentReceipt.getPayment().getAmount(),receiptEntity.getPayment().getAmount());
        assertEquals(paymentReceipt.getPayment().getType(),receiptEntity.getPayment().getType());
    }
}