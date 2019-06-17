package com.wirecard.payment.repositories.data;

import com.wirecard.payment.entity.enumerator.CardIssuer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Card {
    private String holdersName;
    private String number;
    private LocalDate expirationDate;
    private String cvv;
    private CardIssuer issuer;
}
