package com.wirecard.payment.http.converter.impl;

import com.wirecard.payment.entity.*;
import com.wirecard.payment.entity.enumerator.PaymentType;
import com.wirecard.payment.http.converter.ReceiptConverter;
import com.wirecard.payment.http.data.request.PaymentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
public class ReceiptConverterImpl implements ReceiptConverter {

    @Override
    public PaymentReceipt convertToUseCase(PaymentRequest paymentRequest) {
        if(isNull(paymentRequest)) return null;

       return PaymentReceipt.builder()
               .client(buildClient(paymentRequest.getClient()))
               .buyer(buildBuyer(paymentRequest.getBuyer()))
               .payment(buildPayment(paymentRequest.getPayment()))
               .build();
    }

    private Client buildClient(com.wirecard.payment.http.data.request.Client clientRequest) {
        return Client.builder()
                .id(clientRequest.getId())
                .build();
    }

    private Buyer buildBuyer(com.wirecard.payment.http.data.request.Buyer buyerRequest) {
        return Buyer.builder()
                .cpf(buyerRequest.getCpf())
                .email(buyerRequest.getEmail())
                .name(buyerRequest.getName())
                .build();
    }

    private Payment buildPayment(com.wirecard.payment.http.data.request.Payment paymentRequest) {
        return Payment.builder()
                .type(paymentRequest.getType())
                .amount(paymentRequest.getAmount())
                .card(paymentRequest.getType().equals(PaymentType.CREDIT_CARD)?buildCard(paymentRequest.getCard()):null)
                .boleto(paymentRequest.getType().equals(PaymentType.BOLETO)?buildBoleto(paymentRequest.getBoleto()):null)
                .build();
    }

    private Card buildCard(com.wirecard.payment.http.data.request.Card cardRequest) {
        if(isNull(cardRequest)) return null;
        return Card.builder()
                .number(cardRequest.getNumber())
                .holdersName(cardRequest.getHoldersName())
                .cvv(cardRequest.getCvv())
                .expirationDate(cardRequest.getExpirationDate())
                .build();
    }

    private Boleto buildBoleto(com.wirecard.payment.http.data.request.Boleto boletoRequest) {
        if(isNull(boletoRequest)) return null;
        return Boleto.builder()
                .number(boletoRequest.getNumber())
                .build();
    }

}
