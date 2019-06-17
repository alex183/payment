package com.wirecard.payment.repositories.converter.impl;

import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.repositories.converter.ReceiptEntityConverter;
import com.wirecard.payment.repositories.data.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@RequiredArgsConstructor
@Component
public class ReceiptEntityConverterImpl implements ReceiptEntityConverter {

    @Override
    public ReceiptEntity convertToEntity(PaymentReceipt paymentReceipt) {
        if(isNull(paymentReceipt)) return null;

       return ReceiptEntity.builder()
               .id(paymentReceipt.getId())
               .status(paymentReceipt.getStatus())
               .client(buildClient(paymentReceipt.getClient()))
               .buyer(buildBuyer(paymentReceipt.getBuyer()))
               .payment(buildPayment(paymentReceipt.getPayment()))
               .creationDate(nonNull(paymentReceipt.getCreationDate())?paymentReceipt.getCreationDate(): LocalDateTime.now())
               .lastModifiedDate(LocalDateTime.now())
               .build();
    }

    @Override
    public PaymentReceipt convertToUseCase(ReceiptEntity receiptEntity) {
        if(isNull(receiptEntity)) return null;

        return PaymentReceipt.builder()
                .id(receiptEntity.getId())
                .status(receiptEntity.getStatus())
                .client(buildClient(receiptEntity.getClient()))
                .buyer(buildBuyer(receiptEntity.getBuyer()))
                .payment(buildPayment(receiptEntity.getPayment()))
                .creationDate(receiptEntity.getCreationDate())
                .lastModifiedDate(receiptEntity.getLastModifiedDate())
                .build();
    }

    @Override
    public List<PaymentReceipt> convertToUseCase(List<ReceiptEntity> receiptEntities) {
        if(isNull(receiptEntities)) return null;

        return receiptEntities.stream()
                .map(this::convertToUseCase)
                .collect(Collectors.toList());
    }

    private Client buildClient(com.wirecard.payment.entity.Client clientEntity) {
        return Client.builder()
                .id(clientEntity.getId())
                .build();
    }

    private Buyer buildBuyer(com.wirecard.payment.entity.Buyer buyerEntity) {
        return Buyer.builder()
                .cpf(buyerEntity.getCpf())
                .email(buyerEntity.getEmail())
                .name(buyerEntity.getName())
                .build();
    }

    private Payment buildPayment(com.wirecard.payment.entity.Payment paymentEntity) {
        return Payment.builder()
                .type(paymentEntity.getType())
                .amount(paymentEntity.getAmount())
                .card(buildCard(paymentEntity.getCard()))
                .boleto(buildBoleto(paymentEntity.getBoleto()))
                .build();
    }

    private Boleto buildBoleto(com.wirecard.payment.entity.Boleto boleto) {
        if(isNull(boleto)) return null;
        return Boleto.builder()
                .number(boleto.getNumber())
                .build();
    }


    private Card buildCard(com.wirecard.payment.entity.Card card) {
        if(isNull(card)) return null;
        return Card.builder()
                .number(card.getNumber())
                .holdersName(card.getHoldersName())
                .cvv(card.getCvv())
                .expirationDate(card.getExpirationDate())
                .issuer(card.getIssuer())
                .build();
    }

    private com.wirecard.payment.entity.Client buildClient(Client client) {
        return com.wirecard.payment.entity.Client.builder()
                .id(client.getId())
                .build();
    }

    private com.wirecard.payment.entity.Buyer buildBuyer(Buyer buyer) {
        return com.wirecard.payment.entity.Buyer.builder()
                .cpf(buyer.getCpf())
                .email(buyer.getEmail())
                .name(buyer.getName())
                .build();
    }

    private com.wirecard.payment.entity.Payment buildPayment(Payment payment) {
        return com.wirecard.payment.entity.Payment.builder()
                .type(payment.getType())
                .amount(payment.getAmount())
                .card(buildCard(payment.getCard()))
                .boleto(buildBoleto(payment.getBoleto()))
                .build();
    }

    private com.wirecard.payment.entity.Card buildCard(Card card) {
        if(isNull(card)) return null;
        return com.wirecard.payment.entity.Card.builder()
                .number(card.getNumber())
                .holdersName(card.getHoldersName())
                .cvv(card.getCvv())
                .expirationDate(card.getExpirationDate())
                .issuer(card.getIssuer())
                .build();
    }

    private com.wirecard.payment.entity.Boleto buildBoleto(Boleto boleto) {
        if(isNull(boleto)) return null;
        return com.wirecard.payment.entity.Boleto.builder()
                .number(boleto.getNumber())
                .build();
    }
}
