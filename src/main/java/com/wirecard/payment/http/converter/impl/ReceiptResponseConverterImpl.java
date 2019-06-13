package com.wirecard.payment.http.converter.impl;

import com.wirecard.payment.entity.PaymentReceipt;
import com.wirecard.payment.http.converter.ReceiptResponseConverter;
import com.wirecard.payment.http.data.response.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.wirecard.payment.entity.enumerator.PaymentType.BOLETO;
import static com.wirecard.payment.entity.enumerator.PaymentType.CREDIT_CARD;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

@RequiredArgsConstructor
@Component
public class ReceiptResponseConverterImpl implements ReceiptResponseConverter {

    @Override
    public ReceiptResponse convertToResponse(PaymentReceipt paymentReceipt) {
        if(isNull(paymentReceipt)) return null;

       return ReceiptResponse.builder()
               .id(paymentReceipt.getId())
               .status(paymentReceipt.getStatus())
               .client(buildClient(paymentReceipt.getClient()))
               .buyer(buildBuyer(paymentReceipt.getBuyer()))
               .payment(buildPayment(paymentReceipt.getPayment()))
               .creationDate(paymentReceipt.getCreationDate())
               .lastModifiedDate(paymentReceipt.getLastModifiedDate())
               .build();
    }

    @Override
    public List<ReceiptResponse> convertToResponse(List<PaymentReceipt> receipts) {
        if(isNull(receipts)) return null;

        return receipts.stream().map(this::convertToResponse).collect(toList());
    }

    private Client buildClient(com.wirecard.payment.entity.Client client) {
        return Client.builder()
                .id(client.getId())
                .build();
    }

    private Buyer buildBuyer(com.wirecard.payment.entity.Buyer buyer) {
        return Buyer.builder()
                .cpf(buyer.getCpf())
                .email(buyer.getEmail())
                .name(buyer.getName())
                .build();
    }

    private Payment buildPayment(com.wirecard.payment.entity.Payment payment) {
        return Payment.builder()
                .type(payment.getType())
                .amount(payment.getAmount())
                .card(CREDIT_CARD.equals(payment.getType())?buildCard(payment.getCard()):null)
                .boleto(BOLETO.equals(payment.getType())?buildBoleto(payment.getBoleto()):null)
                .build();
    }

    private Card buildCard(com.wirecard.payment.entity.Card card) {
        if(isNull(card)) return null;
        return Card.builder()
                .number(card.getNumber())
                .holdersName(card.getHoldersName())
                .cvv(card.getCvv())
                .expirationDate(card.getExpirationDate())
                .build();
    }

    private Boleto buildBoleto(com.wirecard.payment.entity.Boleto boleto) {
        if(isNull(boleto)) return null;
        return Boleto.builder()
                .number(boleto.getNumber())
                .build();
    }

}
