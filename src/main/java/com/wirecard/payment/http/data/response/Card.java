package com.wirecard.payment.http.data.response;

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
}
