package com.wirecard.payment.mock;

import com.wirecard.payment.entity.*;
import com.wirecard.payment.entity.enumerator.PaymentType;
import com.wirecard.payment.http.data.request.PaymentRequest;
import com.wirecard.payment.http.data.response.PaymentReceiptResponse;
import com.wirecard.payment.repositories.data.ReceiptEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Mocks {

    public static PaymentReceipt getPaymentReceiptMock(){
        return PaymentReceipt.builder()
                .buyer(Buyer.builder()
                        .cpf("37594386876")
                        .email("abc@ab.com")
                        .name("John Doe")
                        .build())
                .client(Client.builder()
                        .id("1234")
                        .build())
                .payment(Payment.builder()
                        .amount(BigDecimal.TEN)
                        .type(PaymentType.BOLETO)
                        .boleto(Boleto.builder()
                                .number("4321")
                                .build())
                        .build())
                .creationDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    public static PaymentReceipt getPaymentReceiptCreditCardMock(){
        return PaymentReceipt.builder()
                .buyer(Buyer.builder()
                        .cpf("37594386876")
                        .email("abc@ab.com")
                        .name("John Doe")
                        .build())
                .client(Client.builder()
                        .id("1234")
                        .build())
                .payment(Payment.builder()
                        .amount(BigDecimal.TEN)
                        .type(PaymentType.CREDIT_CARD)
                        .card(Card.builder()
                                .cvv("321")
                                .holdersName("asa asa")
                                .number("12345678901234")
                                .expirationDate(LocalDate.now())
                                .build())
                        .build())
                .creationDate(LocalDateTime.now())
                .lastModifiedDate(LocalDateTime.now())
                .build();
    }

    public static ReceiptEntity getReceiptEntityMock(){
        return ReceiptEntity.builder()
                .buyer(com.wirecard.payment.repositories.data.Buyer.builder()
                        .cpf("37594386876")
                        .email("abc@ab.com")
                        .name("John Doe")
                        .build())
                .client(com.wirecard.payment.repositories.data.Client.builder()
                        .id("1234")
                        .build())
                .payment(com.wirecard.payment.repositories.data.Payment.builder()
                        .amount(BigDecimal.TEN)
                        .type(PaymentType.BOLETO)
                        .boleto(com.wirecard.payment.repositories.data.Boleto.builder()
                                .number("4321")
                                .build())
                        .build())
                .build();
    }

    public static ReceiptEntity getReceiptEntityCreditCardMock(){
        return ReceiptEntity.builder()
                .buyer(com.wirecard.payment.repositories.data.Buyer.builder()
                        .cpf("37594386876")
                        .email("abc@ab.com")
                        .name("John Doe")
                        .build())
                .client(com.wirecard.payment.repositories.data.Client.builder()
                        .id("123")
                        .build())
                .payment(com.wirecard.payment.repositories.data.Payment.builder()
                        .amount(BigDecimal.TEN)
                        .type(PaymentType.CREDIT_CARD)
                        .card(com.wirecard.payment.repositories.data.Card.builder()
                                .cvv("321")
                                .holdersName("asa asa")
                                .number("12345678901234")
                                .expirationDate(LocalDate.now())
                                .build())
                        .build())
                .build();
    }

    public static PaymentReceiptResponse getReceiptResponseCreditCardMock(){
        return PaymentReceiptResponse.builder()
                .buyer(com.wirecard.payment.http.data.response.Buyer.builder()
                        .cpf("37594386876")
                        .email("abc@ab.com")
                        .name("John Doe")
                        .build())
                .client(com.wirecard.payment.http.data.response.Client.builder()
                        .id("123")
                        .build())
                .payment(com.wirecard.payment.http.data.response.Payment.builder()
                        .amount(BigDecimal.TEN)
                        .type(PaymentType.CREDIT_CARD)
                        .card(com.wirecard.payment.http.data.response.Card.builder()
                                .cvv("321")
                                .holdersName("asa asa")
                                .number("12345678901234")
                                .expirationDate(LocalDate.now())
                                .build())
                        .build())
                .build();
    }

    public static PaymentRequest getPaymentRequestCreditCardMock(){
        return PaymentRequest.builder()
                .buyer(com.wirecard.payment.http.data.request.Buyer.builder()
                        .cpf("37594386876")
                        .email("abc@ab.com")
                        .name("John Doe")
                        .build())
                .client(com.wirecard.payment.http.data.request.Client.builder()
                        .id("123")
                        .build())
                .payment(com.wirecard.payment.http.data.request.Payment.builder()
                        .amount(BigDecimal.TEN)
                        .type(PaymentType.CREDIT_CARD)
                        .card(com.wirecard.payment.http.data.request.Card.builder()
                                .cvv("321")
                                .holdersName("asa asa")
                                .number("12345678901234")
                                .expirationDate(LocalDate.now())
                                .build())
                        .build())
                .build();
    }

}
